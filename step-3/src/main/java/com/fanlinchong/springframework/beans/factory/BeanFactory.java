package com.fanlinchong.springframework.beans.factory;

import com.fanlinchong.springframework.beans.BeansException;

public interface BeanFactory {
	/**
	 * 根据名字获取bean实例
	 *
	 * @param name
	 * @return
	 */
	Object getBean(String name) throws BeansException;

	/**
	 * 获取有参构造函数的bean实例
	 *
	 * @param name 名字
	 * @param args 构造函数的参数
	 * @return
	 * @throws BeansException
	 */
	Object getBean(String name, Object... args) throws BeansException;
}
