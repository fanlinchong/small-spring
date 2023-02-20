package com.fanlinchong.springframework.core.io;

public interface ResourceLoader {
	/**
	 * Pseudo URL prefix for loading from the class path: "classpath:"
	 */
	String CLASSPATH_PREFIX = "classpath:";

	Resource getResource(String location);
}
