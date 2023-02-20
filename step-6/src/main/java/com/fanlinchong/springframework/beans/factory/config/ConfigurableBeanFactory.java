package com.fanlinchong.springframework.beans.factory.config;

import com.fanlinchong.springframework.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {
	String SCOPE_SINGLETON = "singleton";
	String SCOPE_PROTOTYPE = "prototype";

	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
