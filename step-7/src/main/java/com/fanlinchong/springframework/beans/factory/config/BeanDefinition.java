package com.fanlinchong.springframework.beans.factory.config;

import com.fanlinchong.springframework.beans.PropertyValues;

/**
 * xml 可以配置 init-method="initDataMethod" destroy-method="destroyDataMethod"
 */
public class BeanDefinition {
	private final Class beanClass;

	private final PropertyValues propertyValues;

	private String initMethodName;

	private String destroyMethodName;


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

	public String getInitMethodName() {
		return initMethodName;
	}

	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}

	public String getDestroyMethodName() {
		return destroyMethodName;
	}

	public void setDestroyMethodName(String destroyMethodName) {
		this.destroyMethodName = destroyMethodName;
	}
}
