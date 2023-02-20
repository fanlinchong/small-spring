package com.fanlinchong.springframework.test.bean;

import com.fanlinchong.springframework.beans.factory.DisposableBean;
import com.fanlinchong.springframework.beans.factory.InitializingBean;

public class UserService implements InitializingBean, DisposableBean {
	private String userId;

	private String company;

	private String location;

	private UserDao userDao;


	public String queryUserInfo() {
		String userName = userDao.queryUserName(userId);

		return "用户名：" + userName + ", 公司：" + company + "，地点：" + location;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String toString() {
		return "{" +
				"userId='" + userId + '\'' +
				", company='" + company + '\'' +
				", location='" + location + '\'' +
				", userDao=" + userDao +
				'}';
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("execute method named 'destroy'");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("execute method named 'afterPropertiesSet'");
	}
}
