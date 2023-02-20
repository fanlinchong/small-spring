package com.fanlinchong.springframework.beans.factory.support;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.DisposableBean;
import com.fanlinchong.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	/**
	 * Internal marker for a null singleton object:
	 * used as marker value for concurrent Maps (which don't support null values).
	 */
	protected static final Object NULL_OBJECT = new Object();
	private final Map<String, Object> singletonObjects = new HashMap<>();
	private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

	@Override
	public Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}

	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		singletonObjects.put(beanName, singletonObject);
	}

	public void registerDisposableBean(String beanName, DisposableBean bean) {
		this.disposableBeanMap.put(beanName, bean);
	}


	public void destroySingletons() {
		Object[] disposableBeanNames = this.disposableBeanMap.keySet().toArray();
		for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
			Object beanName = disposableBeanNames[i];
			DisposableBean disposableBean = disposableBeanMap.remove(beanName);
			try {
				disposableBean.destroy();
			} catch (Exception e) {
				throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
			}
		}
	}
}
