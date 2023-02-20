package com.fanlinchong.springframework.test;

import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.fanlinchong.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.fanlinchong.springframework.context.support.ClassPathXmlApplicationContext;
import com.fanlinchong.springframework.test.bean.UserService;
import com.fanlinchong.springframework.test.common.MyBeanFactoryPostProcessor;
import com.fanlinchong.springframework.test.common.MyBeanPostProcessor;
import org.junit.Assert;
import org.junit.Test;

public class ApiTest {
	@Test
	public void test_BeanFactoryPostProcessor() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:spring.xml");

		MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
		beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

		MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
		beanFactory.addBeanPostProcessor(beanPostProcessor);

		UserService userService = beanFactory.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果：" + result);


		Assert.assertTrue("大而不倒公司".equals(userService.getCompany()));
		Assert.assertTrue("加利福尼亚".equals(userService.getLocation()));
	}


	@Test
	public void test_XmlApplicationContext() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springWithPostProcessor.xml");

		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果：" + result);


		Assert.assertTrue("大而不倒公司".equals(userService.getCompany()));
		Assert.assertTrue("加利福尼亚".equals(userService.getLocation()));
	}

}
