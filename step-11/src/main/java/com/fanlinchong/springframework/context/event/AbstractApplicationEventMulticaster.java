package com.fanlinchong.springframework.context.event;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.BeanFactory;
import com.fanlinchong.springframework.beans.factory.BeanFactoryAware;
import com.fanlinchong.springframework.context.ApplicationEvent;
import com.fanlinchong.springframework.context.ApplicationListener;
import com.fanlinchong.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
	private BeanFactory beanFactory;
	private Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void addApplicationListener(ApplicationListener<?> listener) {
		this.applicationListeners.add(((ApplicationListener<ApplicationEvent>) listener));
	}

	@Override
	public void removeApplicationListener(ApplicationListener<?> listener) {
		this.applicationListeners.remove(listener);
	}

	protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
		List<ApplicationListener> allListeners = new LinkedList<>();
		for (ApplicationListener<ApplicationEvent> listener : this.applicationListeners) {
			if (supportsEvent(listener, event)) {
				allListeners.add(listener);
			}
		}

		return allListeners;
	}

	private boolean supportsEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) {
		Class<? extends ApplicationListener> listenerClass = listener.getClass();

		Class<?> targetClass = ClassUtils.isCglibProxy(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
		Type genericInterface = targetClass.getGenericInterfaces()[0];

		Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
		String className = actualTypeArgument.getTypeName();
		Class<?> eventClass = null;
		try {
			eventClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new BeansException("wrong event class name: " + className);
		}

		return eventClass.isAssignableFrom(event.getClass());
	}
}
