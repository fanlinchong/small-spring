package com.fanlinchong.springframework.test.bean;

public class UserService {


	private IUserDao userDao;


	public User queryUser(Long userId) {
		return userDao.queryUser(userId);
	}
}
