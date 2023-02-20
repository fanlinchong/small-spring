package com.fanlinchong.springframework.beans.factory.support;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.config.BeanPostProcessor;
import com.fanlinchong.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.fanlinchong.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
	private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

	private final ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	@Override
	public Object getBean(String beanName) throws BeansException {
		return doGetBean(beanName, null);
	}

	@Override
	public Object getBean(String beanName, Object... args) throws BeansException {
		return doGetBean(beanName, args);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return (T) getBean(name);
	}

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		this.beanPostProcessorList.remove(beanPostProcessor);
		this.beanPostProcessorList.add(beanPostProcessor);
	}

	public List<BeanPostProcessor> getBeanPostProcessorList() {
		return beanPostProcessorList;
	}

	protected <T> T doGetBean(final String beanName, final Object[] args) {
		Object bean = getSingleton(beanName);
		if (bean != null) {
			return (T) bean;
		}

		BeanDefinition beanDefinition = getBeanDefinition(beanName);
		return (T) createBean(beanName, beanDefinition, args);
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;
}
