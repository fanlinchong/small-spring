package com.fanlinchong.springframework.test.common;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.config.BeanPostProcessor;
import com.fanlinchong.springframework.test.bean.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if ("userService".equals(beanName)) {
			UserService userService = (UserService) bean;
			userService.setLocation("加利福尼亚");
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
