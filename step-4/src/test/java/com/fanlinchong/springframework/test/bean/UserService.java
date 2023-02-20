package com.fanlinchong.springframework.test.bean;

public class UserService {
	private String userId;

	private UserDao userDao;


	public String queryUserName() {
		String userName = userDao.queryUserName(userId);
		System.out.println("查询用户信息：" + userName);

		return userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
