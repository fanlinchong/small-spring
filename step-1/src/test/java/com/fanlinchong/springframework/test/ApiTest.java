package com.fanlinchong.springframework.test;

import com.fanlinchong.springframework.BeanDefinition;
import com.fanlinchong.springframework.BeanFactory;
import com.fanlinchong.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

	@Test
	public void testBeanFactory() {
		BeanFactory beanFactory = new BeanFactory();

		BeanDefinition beanDefinition = new BeanDefinition(new UserService());
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		UserService userService = (UserService) beanFactory.getBean("userService");
		userService.queryUserInfo();
	}
}
