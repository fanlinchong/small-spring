package com.fanlinchong.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
	private static final Map<String, String> map = new HashMap<>();

	public void initData() {
		System.out.println("initializing data...");
		map.put("10001", "zhang san");
		map.put("10002", "li si");
		map.put("10003", "wang wu");
		map.put("10004", "zhao liu");
	}

	public void destroyData() {
		System.out.println("destroying data...");
		map.clear();
	}

	public String queryUserName(String userId) {
		return map.get(userId);
	}
}
