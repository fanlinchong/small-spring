package com.fanlinchong.springframework.test.bean;

import com.fanlinchong.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
	private static int callCount = 0;

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		callCount++;
		if (callCount % 10 == 0) {
			System.out.printf("[%s] 已调用 %d 次\r\n", method.getName(), callCount);
		}
	}
}
