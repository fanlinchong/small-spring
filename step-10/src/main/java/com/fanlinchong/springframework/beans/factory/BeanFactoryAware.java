package com.fanlinchong.springframework.beans.factory;

public interface BeanFactoryAware extends Aware {
	void setBeanFactory(BeanFactory beanFactory);
}
