package com.fanlinchong.springframework.test;

import com.fanlinchong.springframework.beans.PropertyValue;
import com.fanlinchong.springframework.beans.PropertyValues;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.config.BeanReference;
import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.fanlinchong.springframework.test.bean.UserDao;
import com.fanlinchong.springframework.test.bean.UserService;
import org.junit.Assert;
import org.junit.Test;

public class ApiTest {

	@Test
	public void test_BeanFactory() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// register UserDao
		beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
		// register UserService
		PropertyValues propertyValues = new PropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("userId", "10003"));
		propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
		beanFactory.registerBeanDefinition("userService", new BeanDefinition(UserService.class, propertyValues));


		UserService userService = (UserService) beanFactory.getBean("userService");

		Assert.assertTrue("wang wu".equals(userService.queryUserName()));
	}
}
