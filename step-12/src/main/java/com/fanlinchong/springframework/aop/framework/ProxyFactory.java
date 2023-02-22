package com.fanlinchong.springframework.aop.framework;

import com.fanlinchong.springframework.aop.AdvisedSupport;

public class ProxyFactory {
	private final AdvisedSupport advisedSupport;

	public ProxyFactory(AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}

	public Object getProxy() {
		return createAopProxy().getProxy();
	}

	private AopProxy createAopProxy() {
		if (this.advisedSupport.isProxyTargetClass()) {
			return new Cglib2AopProxy(advisedSupport);
		}

		return new JdkDynamicAopProxy(advisedSupport);
	}
}
