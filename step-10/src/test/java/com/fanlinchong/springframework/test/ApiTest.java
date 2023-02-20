package com.fanlinchong.springframework.test;

import com.fanlinchong.springframework.context.support.ClassPathXmlApplicationContext;
import com.fanlinchong.springframework.test.bean.CustomEvent;
import org.junit.Test;

public class ApiTest {


	@Test
	public void test_Event() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.publishEvent(new CustomEvent(applicationContext, 10086, "中国移动"));
		applicationContext.registerShutdownHook();
	}

}
