package com.fanlinchong.springframework.test;

import cn.hutool.core.lang.Assert;
import com.fanlinchong.springframework.context.support.ClassPathXmlApplicationContext;
import com.fanlinchong.springframework.test.bean.User;
import com.fanlinchong.springframework.test.bean.UserService;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class ApiTest {


	@Test
	public void test_Prototype() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();

		UserService userServiceA = applicationContext.getBean("userService", UserService.class);
		UserService userServiceB = applicationContext.getBean("userService", UserService.class);
		System.out.println("A: " + Integer.toHexString(userServiceA.hashCode()) + ", B: " + Integer.toHexString(userServiceB.hashCode()));

		System.out.println(ClassLayout.parseInstance(userServiceA).toPrintable());

		Assert.isFalse(userServiceA == userServiceB);
	}


	@Test
	public void test_FactoryBean() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();

		UserService userService = applicationContext.getBean("userService", UserService.class);
		User user = userService.queryUser(10003L);
		Assert.isTrue(user != null && "wang wu".equals(user.getUserName()));
	}

}
