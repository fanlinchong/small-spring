package com.fanlinchong.springframework.context.support;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.fanlinchong.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.fanlinchong.springframework.beans.factory.config.BeanPostProcessor;
import com.fanlinchong.springframework.context.ApplicationEvent;
import com.fanlinchong.springframework.context.ApplicationListener;
import com.fanlinchong.springframework.context.ConfigurableApplicationContext;
import com.fanlinchong.springframework.context.event.ApplicationEventMulticaster;
import com.fanlinchong.springframework.context.event.ContextClosedEvent;
import com.fanlinchong.springframework.context.event.ContextRefreshedEvent;
import com.fanlinchong.springframework.context.event.SimpleApplicationEventMulticaster;
import com.fanlinchong.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
	public static final String APPLICATION_EVENT_MULTICASTER_NEAN_NAME = "applicationEventMulticaster";
	private ApplicationEventMulticaster applicationEventMulticaster;

	@Override
	public void refresh() throws BeansException {
		// 1. 创建 BeanFactory，并加载 BeanDefinition
		refreshBeanFactory();
		// 2. 获取 BeanFactory
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		// 3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
		// 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
		invokeBeanFactoryPostProcessors(beanFactory);
		// 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
		registerBeanPostProcessors(beanFactory);
		// 6. 初始化事件发布者
		initApplicationEventMulticaster();
		// 7. 注册事件监听器
		registerListeners();
		// 8. 提前实例化单例 Bean 对象
		beanFactory.preInstantiateSingletons();
		// 9. 发布容器刷新完成事件
		finishRefresh();
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(name, requiredType);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return getBeanFactory().getBean(name, args);
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return getBeanFactory().getBean(name);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return getBeanFactory().getBeansOfType(type);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	@Override
	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}

	@Override
	public void close() {
		// 发布容器关闭事件
		publishEvent(new ContextClosedEvent(this));
		// 执行销毁单例bean的销毁方法
		getBeanFactory().destroySingletons();
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		this.applicationEventMulticaster.multicastEvent(event);
	}

	/**
	 * 创建 BeanFactory，并加载 BeanDefinition
	 *
	 * @throws BeansException
	 */
	protected abstract void refreshBeanFactory() throws BeansException;

	/**
	 * 获取 BeanFactory
	 *
	 * @return
	 */
	protected abstract ConfigurableListableBeanFactory getBeanFactory();


	private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
		for (BeanFactoryPostProcessor processor : beanFactoryPostProcessorMap.values()) {
			processor.postProcessBeanFactory(beanFactory);
		}
	}

	private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
		for (BeanPostProcessor processor : beanPostProcessorMap.values()) {
			beanFactory.addBeanPostProcessor(processor);
		}
	}


	private void initApplicationEventMulticaster() {
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
		beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_NEAN_NAME, applicationEventMulticaster);
	}

	private void registerListeners() {
		Collection<ApplicationListener> listeners = getBeansOfType(ApplicationListener.class).values();
		listeners.forEach(listener -> applicationEventMulticaster.addApplicationListener(listener));
	}

	private void finishRefresh() {
		publishEvent(new ContextRefreshedEvent(this));
	}


}
