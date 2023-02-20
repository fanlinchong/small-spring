package com.fanlinchong.springframework.test;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.fanlinchong.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

	@Test
	public void testBeanFactory() throws BeansException {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		UserService userService = (UserService) beanFactory.getBean("userService");
		userService.queryUserInfo();

		UserService userService_Singleton = (UserService) beanFactory.getBean("userService");
		userService_Singleton.queryUserInfo();
	}
}
