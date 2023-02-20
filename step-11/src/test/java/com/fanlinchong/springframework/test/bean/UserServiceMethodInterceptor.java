package com.fanlinchong.springframework.test.bean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UserServiceMethodInterceptor implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		long start = System.currentTimeMillis();
		UserService service = (UserService) methodInvocation.getThis();
		User user = ((User) methodInvocation.getArguments()[0]);
		for (User u : service.userList) {
			if (u.getId().equals(user.getId())) {
				System.out.println("用户已存在, id: " + user.getId());
				return null;
			}
		}


		try {
			return methodInvocation.proceed();
		} finally {
			System.out.println("监控 - Begin By Aop");
			System.out.println("方法名称：" + methodInvocation.getMethod());
			System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
			System.out.println("监控 - End\r\n");
		}
	}
}
