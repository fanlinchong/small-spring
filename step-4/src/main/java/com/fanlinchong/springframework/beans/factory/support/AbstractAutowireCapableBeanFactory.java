package com.fanlinchong.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.PropertyValue;
import com.fanlinchong.springframework.beans.PropertyValues;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
		Object bean = null;
		try {
			bean = createBeanInstance(beanDefinition, beanName, args);

			applyPropertyValues(beanName, bean, beanDefinition);
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
}
