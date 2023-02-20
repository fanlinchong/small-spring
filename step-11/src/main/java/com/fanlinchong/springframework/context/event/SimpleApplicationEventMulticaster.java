package com.fanlinchong.springframework.context.event;

import com.fanlinchong.springframework.beans.factory.BeanFactory;
import com.fanlinchong.springframework.context.ApplicationEvent;
import com.fanlinchong.springframework.context.ApplicationListener;

import java.util.Collection;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
	public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
		setBeanFactory(beanFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void multicastEvent(ApplicationEvent event) {
		Collection<ApplicationListener> listeners = getApplicationListeners(event);
		listeners.forEach(listener -> listener.onApplicationEvent(event));
	}
}
