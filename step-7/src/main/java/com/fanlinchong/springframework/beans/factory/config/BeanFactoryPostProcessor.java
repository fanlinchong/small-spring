package com.fanlinchong.springframework.beans.factory.config;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
	/**
	 * 在所有的bean定义加载完后，实例化 bean 对象之前，提供修改 BeanDefinition 属性的机制
	 *
	 * @param beanFactory
	 */
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
