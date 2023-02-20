package com.fanlinchong.springframework.test.bean;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.BeanClassLoaderAware;
import com.fanlinchong.springframework.beans.factory.BeanFactory;
import com.fanlinchong.springframework.beans.factory.BeanFactoryAware;
import com.fanlinchong.springframework.beans.factory.BeanNameAware;
import com.fanlinchong.springframework.context.ApplicationContext;
import com.fanlinchong.springframework.context.ApplicationContextAware;

public class UserService implements BeanFactoryAware, BeanNameAware, BeanClassLoaderAware, ApplicationContextAware {
	private BeanFactory beanFactory;
	private ApplicationContext applicationContext;

	private String beanName;

	private ClassLoader classLoader;

	private String company;

	private String location;

	private UserDao userDao;


	public String queryUserName(String userId) {
		return userDao.queryUserName(userId);
	}


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String toString() {
		return "{" +
				"company='" + company + '\'' +
				", location='" + location + '\'' +
				", userDao=" + userDao +
				'}';
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("ClassLoader：" + classLoader);
		this.classLoader = classLoader;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public String getBeanName() {
		return beanName;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
		System.out.println("beanName is " + beanName);
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}
}
