package com.fanlinchong.springframework.test;

import cn.hutool.core.lang.Assert;
import com.fanlinchong.springframework.aop.AdvisedSupport;
import com.fanlinchong.springframework.aop.TargetSource;
import com.fanlinchong.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.fanlinchong.springframework.aop.framework.Cglib2AopProxy;
import com.fanlinchong.springframework.aop.framework.JdkDynamicAopProxy;
import com.fanlinchong.springframework.test.bean.IUserService;
import com.fanlinchong.springframework.test.bean.User;
import com.fanlinchong.springframework.test.bean.UserService;
import com.fanlinchong.springframework.test.bean.UserServiceMethodInterceptor;
import org.junit.Test;

public class ApiTest {


	@Test
	public void test_DynamicProxy() {
		IUserService userService = new UserService();

		AdvisedSupport advisedSupport = new AdvisedSupport();
		advisedSupport.setTargetSource(new TargetSource(userService));
		advisedSupport.setMethodInterceptor(new UserServiceMethodInterceptor());
		advisedSupport.setMethodMatcher(new AspectJExpressionPointcut(
				"execution(* com.fanlinchong.springframework.test.bean.IUserService.registerUser(..))"
		));

		int originalCount = userService.count();

		IUserService userServiceByDynamicProxy = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
		userServiceByDynamicProxy.registerUser(new User(10001L, "A"));

		Assert.isTrue(userServiceByDynamicProxy.count() == originalCount);

		IUserService userServiceByCglibProxy = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
		userServiceByCglibProxy.registerUser(new User(10010L, "AAA"));

		Assert.isTrue(userServiceByCglibProxy.count() == originalCount + 1);

	}

}
