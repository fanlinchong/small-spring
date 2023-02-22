package com.fanlinchong.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.DisposableBean;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {
	private final Object bean;
	private final String beanName;
	private final String destroyMethodName;

	public DisposableBeanAdapter(Object bean, String beanName, String destroyMethodName) {
		this.bean = bean;
		this.beanName = beanName;
		this.destroyMethodName = destroyMethodName;
	}

	@Override
	public void destroy() throws Exception {
		// 1. 实现接口 DisposableBean
		if (bean instanceof DisposableBean) {
			((DisposableBean) bean).destroy();
		}

		// 2. 配置信息 destroy-method {判断是为了避免二次执行销毁}
		if (StrUtil.isNotEmpty(destroyMethodName)
				&& !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
			Method method = bean.getClass().getMethod(destroyMethodName);
			if (method == null) {
				throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
			}

			method.invoke(bean);
		}
	}
}
