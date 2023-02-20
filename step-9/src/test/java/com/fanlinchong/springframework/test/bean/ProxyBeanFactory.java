package com.fanlinchong.springframework.test.bean;

import com.fanlinchong.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ProxyBeanFactory implements FactoryBean<IUserDao> {
	private static final List<User> USER_LIST = new ArrayList<User>() {{
		add(new User(10001L, "zhang san"));
		add(new User(10002L, "li si"));
		add(new User(10003L, "wang wu"));
		add(new User(10004L, "zhao liu"));
	}};

	@Override
	public IUserDao getObject() throws Exception {
		InvocationHandler queryUserInvocationHandler = (proxy, method, args) -> {
			Long userId = (Long) args[0];
			for (User user : USER_LIST) {
				if (user.getId().equals(userId)) {
					return user;
				}
			}

			return null;
		};

		return (IUserDao) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, queryUserInvocationHandler
		);
	}

	@Override
	public Class<?> getObjectType() {
		return IUserDao.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
