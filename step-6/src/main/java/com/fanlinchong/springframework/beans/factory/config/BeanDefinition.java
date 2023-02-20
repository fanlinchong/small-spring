package com.fanlinchong.springframework.beans.factory.config;

import com.fanlinchong.springframework.beans.PropertyValues;

public class BeanDefinition {
	private final Class beanClass;

	private final PropertyValues propertyValues;


	public BeanDefinition(Class beanClass) {
		this.beanClass = beanClass;
		this.propertyValues = new PropertyValues();
	}

	public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
		this.beanClass = beanClass;
		this.propertyValues = propertyValues == null ? new PropertyValues() : propertyValues;
	}

	public Class getBeanClass() {
		return beanClass;
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}
}
