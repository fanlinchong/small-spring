package com.fanlinchong.springframework.beans.factory;

public interface BeanNameAware extends Aware {
	void setBeanName(String beanName);
}
