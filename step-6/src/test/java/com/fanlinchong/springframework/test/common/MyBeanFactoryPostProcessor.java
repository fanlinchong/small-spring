package com.fanlinchong.springframework.test.common;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.PropertyValue;
import com.fanlinchong.springframework.beans.PropertyValues;
import com.fanlinchong.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
		PropertyValues propertyValues = beanDefinition.getPropertyValues();

		propertyValues.addPropertyValue(new PropertyValue("company", "大而不倒公司"));
	}
}
