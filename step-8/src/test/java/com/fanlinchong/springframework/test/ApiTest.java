package com.fanlinchong.springframework.test;

import cn.hutool.core.lang.Assert;
import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.fanlinchong.springframework.context.support.ClassPathXmlApplicationContext;
import com.fanlinchong.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {


	@Test
	public void test_InitAndDestroy() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();

		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserName("10003");
		Assert.isTrue("wang wu".equals(result));
		Assert.isTrue("userService".equals(userService.getBeanName()));
		Assert.isTrue(userService.getApplicationContext() != null
				&& ClassPathXmlApplicationContext.class.equals(userService.getApplicationContext().getClass()));
		Assert.isTrue(userService.getClassLoader() != null);
		Assert.isTrue(userService.getBeanFactory() != null
				&& DefaultListableBeanFactory.class.equals(userService.getBeanFactory().getClass()));

	}

}
