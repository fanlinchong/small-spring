package com.fanlinchong.springframework.context;

import com.fanlinchong.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {
	/**
	 * 刷新容器
	 *
	 * @throws BeansException
	 */
	void refresh() throws BeansException;

	void registerShutdownHook();

	void close();
}
