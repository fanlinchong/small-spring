package com.fanlinchong.springframework.beans.factory;

public interface InitializingBean {
	/**
	 * bean 属性填充后调用
	 *
	 * @throws Exception
	 */
	void afterPropertiesSet() throws Exception;
}
