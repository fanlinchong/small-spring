package com.fanlinchong.springframework.test;

import cn.hutool.core.lang.Assert;
import com.fanlinchong.springframework.context.support.ClassPathXmlApplicationContext;
import com.fanlinchong.springframework.test.bean.IUserService;
import com.fanlinchong.springframework.test.bean.User;
import org.junit.Test;

public class ApiTest {


	@Test
	public void test_DynamicProxy() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

		IUserService userService = applicationContext.getBean("userService", IUserService.class);

		User user = null;
		for (int i = 0; i < 50; i++) {
			user = userService.getUser(10001L);
			userService.count();
		}

		Assert.isTrue(user != null && user.getUserName().equals("zhang san"));
	}

}
