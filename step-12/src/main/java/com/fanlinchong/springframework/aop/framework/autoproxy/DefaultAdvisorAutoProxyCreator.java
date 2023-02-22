package com.fanlinchong.springframework.aop.framework.autoproxy;

import com.fanlinchong.springframework.aop.*;
import com.fanlinchong.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.fanlinchong.springframework.aop.framework.ProxyFactory;
import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.BeanFactory;
import com.fanlinchong.springframework.beans.factory.BeanFactoryAware;
import com.fanlinchong.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
	private DefaultListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		if (isInfrastructureClass(beanClass)) {
			return null;
		}

		Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
		for (AspectJExpressionPointcutAdvisor advisor : advisors) {
			ClassFilter classFilter = advisor.getPointcut().getClassFilter();
			if (!classFilter.matches(beanClass)) {
				continue;
			}

			AdvisedSupport advisedSupport = new AdvisedSupport();

			TargetSource targetSource = null;
			try {
				targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}

			advisedSupport.setTargetSource(targetSource);
			advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
			advisedSupport.setProxyTargetClass(false);
			advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());

			return new ProxyFactory(advisedSupport).getProxy();
		}

		return null;
	}

	private boolean isInfrastructureClass(Class<?> beanClass) {
		return Advice.class.isAssignableFrom(beanClass)
				|| Pointcut.class.isAssignableFrom(beanClass)
				|| Advisor.class.isAssignableFrom(beanClass);
	}
}
