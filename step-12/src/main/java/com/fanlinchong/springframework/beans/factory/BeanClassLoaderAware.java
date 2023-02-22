package com.fanlinchong.springframework.beans.factory;

public interface BeanClassLoaderAware extends Aware {
	void setBeanClassLoader(ClassLoader classLoader);
}
