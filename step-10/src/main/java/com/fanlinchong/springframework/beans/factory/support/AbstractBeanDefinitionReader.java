package com.fanlinchong.springframework.beans.factory.support;

import com.fanlinchong.springframework.core.io.DefaultResourceLoader;
import com.fanlinchong.springframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

	private BeanDefinitionRegistry registry;
	private ResourceLoader resourceLoader;

	public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this(registry, new DefaultResourceLoader());

	}

	public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		this.registry = registry;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public BeanDefinitionRegistry getRegistry() {
		return this.registry;
	}

	@Override
	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}
}
