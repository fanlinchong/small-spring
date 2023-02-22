package com.fanlinchong.springframework.aop.aspectj;

import com.fanlinchong.springframework.aop.Pointcut;
import com.fanlinchong.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
	// 切面
	private AspectJExpressionPointcut pointcut;
	// 具体的拦截方法
	private Advice advice;
	// 表达式
	private String expression;


	@Override
	public Advice getAdvice() {
		return advice;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	@Override
	public Pointcut getPointcut() {
		if (pointcut == null) {
			pointcut = new AspectJExpressionPointcut(expression);
		}

		return pointcut;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
