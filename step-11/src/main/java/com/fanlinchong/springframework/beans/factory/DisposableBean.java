package com.fanlinchong.springframework.beans.factory;

public interface DisposableBean {
	/**
	 * context 关闭时执行
	 *
	 * @throws Exception
	 */
	void destroy() throws Exception;
}
