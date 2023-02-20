package com.fanlinchong.springframework.beans.factory.support;

import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition) {
		Object bean = null;
		try {
			bean = beanDefinition.getBeanClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		addSingleton(beanName, bean);
		return bean;
	}
}
