package com.fanlinchong.springframework.test;

import com.fanlinchong.springframework.context.support.ClassPathXmlApplicationContext;
import com.fanlinchong.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {


	@Test
	public void test_InitAndDestroy() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();

		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果：" + result);
	}

}
