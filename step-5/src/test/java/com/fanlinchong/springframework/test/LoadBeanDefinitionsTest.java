package com.fanlinchong.springframework.test;

import cn.hutool.core.lang.Assert;
import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.fanlinchong.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.fanlinchong.springframework.core.io.ClassPathResource;
import com.fanlinchong.springframework.test.bean.UserService;
import org.junit.Test;

public class LoadBeanDefinitionsTest {

	@Test
	public void test_LoadBeanDefinitionsFromClassPath() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(new ClassPathResource("spring.xml"));

		UserService userService = beanFactory.getBean("userService", UserService.class);
		Assert.isTrue("10003".equals(userService.getUserId()));
		Assert.isTrue("wang wu".equals(userService.queryUserName()));
	}
}
