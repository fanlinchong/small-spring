package com.fanlinchong.springframework.beans.factory.support;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

	protected Object getCachedObjectForFactoryBean(String beanName) {
		Object obj = factoryBeanObjectCache.get(beanName);
		return obj != NULL_OBJECT ? obj : null;
	}

	protected Object getObjectFormFactoryBean(FactoryBean factory, String beanName) {
		if (factory.isSingleton()) {
			Object object = this.factoryBeanObjectCache.get(beanName);
			if (object == null) {
				object = this.doGetObjectFromFactoryBean(factory, beanName);
				this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
			}

			return object != NULL_OBJECT ? object : null;
		}

		return this.doGetObjectFromFactoryBean(factory, beanName);
	}

	private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) {
		try {
			return factory.getObject();
		} catch (Exception e) {
			throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
		}
	}
}
