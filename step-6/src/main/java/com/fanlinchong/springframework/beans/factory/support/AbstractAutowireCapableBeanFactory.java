package com.fanlinchong.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.PropertyValue;
import com.fanlinchong.springframework.beans.PropertyValues;
import com.fanlinchong.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.config.BeanPostProcessor;
import com.fanlinchong.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
		Object bean = null;
		try {
			bean = createBeanInstance(beanDefinition, beanName, args);
			// 填充属性
			applyPropertyValues(beanName, bean, beanDefinition);
			// 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
			initializeBean(beanName, bean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}
		addSingleton(beanName, bean);
		return bean;
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
		for (BeanPostProcessor processor : getBeanPostProcessorList()) {
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
		for (BeanPostProcessor processor : getBeanPostProcessorList()) {
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

	private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
		// 1. 执行 BeanPostProcessor Before 处理
		Object wrappedBean = applyBeanPostProcessorBeforeInitialization(bean, beanName);
		// 待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
		invokeInitMethods(beanName, wrappedBean, beanDefinition);
		// 2. 执行 BeanPostProcessor After 处理
		wrappedBean = applyBeanPostProcessorAfterInitialization(wrappedBean, beanName);

		return wrappedBean;
	}

	private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {
	}
}
