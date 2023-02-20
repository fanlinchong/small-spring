package com.fanlinchong.springframework.test;

import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.fanlinchong.springframework.test.bean.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ApiTest {

	@Test
	public void test_BeanFactory() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		UserService userService = (UserService) beanFactory.getBean("userService", "ddfx");
		Assert.assertTrue("ddfx".equals(userService.getName()));
	}

	@Test
	public void test_newInstance() throws InstantiationException, IllegalAccessException {
		// 下面是不会通过的，因为没有无参的构造函数
		UserService userService = UserService.class.newInstance();
		userService.queryUserInfo();
	}

	@Test
	public void test_Constructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Class<UserService> clazz = UserService.class;
		Constructor<UserService> declaredConstructor = clazz.getDeclaredConstructor(String.class);
		UserService userService = declaredConstructor.newInstance("ddfx");
		Assert.assertTrue("ddfx".equals(userService.getName()));
	}

	@Test
	public void test_parameterTypes() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Class<UserService> clazz = UserService.class;
		Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
		Constructor<?> constructor = declaredConstructors[0];
		Constructor<UserService> declaredConstructor = clazz.getDeclaredConstructor(constructor.getParameterTypes());
		UserService userService = declaredConstructor.newInstance("ddfx");
		Assert.assertTrue("ddfx".equals(userService.getName()));
	}
}
