package com.fanlinchong.springframework.context.support;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
	private DefaultListableBeanFactory beanFactory;

	@Override
	protected void refreshBeanFactory() throws BeansException {
		DefaultListableBeanFactory beanFactory = this.createBeanFactory();
		loadBeanDefinitions(beanFactory);
		this.beanFactory = beanFactory;
	}

	@Override
	protected ConfigurableListableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

	private DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory();
	}
}
