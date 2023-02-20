package com.fanlinchong.springframework.test.bean;

public interface IUserService {

	User getUser(Long userId);

	void registerUser(User user);

	int count();
}
