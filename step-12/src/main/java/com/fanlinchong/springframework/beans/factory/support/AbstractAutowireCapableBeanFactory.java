package com.fanlinchong.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.PropertyValue;
import com.fanlinchong.springframework.beans.PropertyValues;
import com.fanlinchong.springframework.beans.factory.*;
import com.fanlinchong.springframework.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
		Object bean = null;
		try {
			// 判断是否返回代理 Bean 对象
			bean = resolveBeforeInstantiation(beanName, beanDefinition);
			if (bean != null) {
				return bean;
			}
			bean = createBeanInstance(beanDefinition, beanName, args);
			// 填充属性
			applyPropertyValues(beanName, bean, beanDefinition);
			// 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
			bean = initializeBean(beanName, bean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}
		// 注册实现了 DisposableBean 接口的 Bean 对象
		registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
		if (beanDefinition.isSingleton()) {
			registerSingleton(beanName, bean);
		}
		return bean;
	}

	protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
		Object bean = applyBeanPostProcessorBeforeInitialization(beanDefinition.getBeanClass(), beanName);
		if (bean != null) {
			bean = applyBeanPostProcessorAfterInitialization(bean, beanName);
		}

		return bean;
	}

	public Object applyBeanPostProcessorBeforeInitialization(Class<?> beanClass, String beanName) {
		List<BeanPostProcessor> beanPostProcessorList = this.getBeanPostProcessors();
		for (BeanPostProcessor processor : beanPostProcessorList) {
			if (processor instanceof InstantiationAwareBeanPostProcessor) {
				Object result = ((InstantiationAwareBeanPostProcessor) processor).postProcessBeforeInstantiation(beanClass, beanName);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}


	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}

	@Override
	public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (current == null) {
				return result;
			}

			result = current;
		}
		return result;
	}

	@Override
	public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if (current == null) {
				return result;
			}

			result = current;
		}
		return result;
	}

	protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
		Constructor constructorToUse = null;
		Constructor[] constructors = beanDefinition.getBeanClass().getDeclaredConstructors();
		for (Constructor ctor : constructors) {
			if (args != null && ctor.getParameterTypes().length == args.length) {
				constructorToUse = ctor;
				break;
			}
		}

		return this.getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
	}

	protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
		try {
			PropertyValues propertyValues = beanDefinition.getPropertyValues();
			for (PropertyValue pv : propertyValues.getPropertyValues()) {
				String propertyName = pv.getName();
				Object value = pv.getValue();
				if (value instanceof BeanReference) {
					BeanReference beanReference = (BeanReference) value;
					value = this.getBean(beanReference.getBeanName());
				}

				BeanUtil.setFieldValue(bean, propertyName, value);
			}
		} catch (Exception e) {
			throw new BeansException("Error setting property values：" + beanName, e);
		}
	}

	protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
		if (!beanDefinition.isSingleton()) {
			return;
		}
		if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
			registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition.getDestroyMethodName()));
		}
	}

	private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
		// invokeAwareMethods
		if (bean instanceof Aware) {
			if (bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(this);
			}
			if (bean instanceof BeanClassLoaderAware) {
				((BeanClassLoaderAware) bean).setBeanClassLoader(getClassLoader());
			}
			if (bean instanceof BeanNameAware) {
				((BeanNameAware) bean).setBeanName(beanName);
			}
		}
		// 1. 执行 BeanPostProcessor Before 处理
		Object wrappedBean = applyBeanPostProcessorBeforeInitialization(bean, beanName);
		try {
			// 执行 Bean 对象的初始化方法
			invokeInitMethods(beanName, wrappedBean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
		}
		// 2. 执行 BeanPostProcessor After 处理
		wrappedBean = applyBeanPostProcessorAfterInitialization(wrappedBean, beanName);

		return wrappedBean;
	}

	private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
		if (wrappedBean instanceof InitializingBean) {
			((InitializingBean) wrappedBean).afterPropertiesSet();
		}


		// 2. 配置信息 init-method {判断是为了避免二次执行销毁}
		String initMethodName = beanDefinition.getInitMethodName();
		if (StrUtil.isNotEmpty(initMethodName)) {
			Method method = beanDefinition.getBeanClass().getMethod(initMethodName);
			if (method == null) {
				throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
			}

			method.invoke(wrappedBean);
		}
	}
}