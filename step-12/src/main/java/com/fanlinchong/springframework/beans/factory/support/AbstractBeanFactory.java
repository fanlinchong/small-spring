package com.fanlinchong.springframework.beans.factory.support;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.FactoryBean;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.config.BeanPostProcessor;
import com.fanlinchong.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.fanlinchong.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

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
		this.beanPostProcessors.remove(beanPostProcessor);
		this.beanPostProcessors.add(beanPostProcessor);
	}

	public List<BeanPostProcessor> getBeanPostProcessors() {
		return beanPostProcessors;
	}

	protected <T> T doGetBean(final String beanName, final Object[] args) {
		Object sharedInstance = getSingleton(beanName);
		if (sharedInstance != null) {
			// 如果是 FactoryBean，则需要调用 FactoryBean#getObject
			return (T) getObjectFormBeanInstance(sharedInstance, beanName);
		}

		BeanDefinition beanDefinition = getBeanDefinition(beanName);
		Object bean = createBean(beanName, beanDefinition, args);

		return (T) getObjectFormBeanInstance(bean, beanName);
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

	private Object getObjectFormBeanInstance(Object beanInstance, String beanName) {
		if (!(beanInstance instanceof FactoryBean)) {
			return beanInstance;
		}

		Object object = getCachedObjectForFactoryBean(beanName);
		if (object == null) {
			FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
			object = getObjectFormFactoryBean(factoryBean, beanName);
		}

		return object;
	}

}
