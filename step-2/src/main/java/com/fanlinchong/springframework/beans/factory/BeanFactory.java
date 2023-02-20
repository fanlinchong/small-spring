package com.fanlinchong.springframework.beans.factory;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface BeanFactory {

	/**
	 * 根据名字获取bean实例
	 *
	 * @param name
	 * @return
	 */
	Object getBean(String name) throws BeansException;
}
