package com.fanlinchong.springframework.test.bean;

public class User {
	private Long id;

	private String userName;
	private String company;

	private String location;

	public User() {
	}

	public User(Long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public User(Long id, String userName, String company, String location) {
		this.id = id;
		this.userName = userName;
		this.company = company;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", company='" + company + '\'' +
				", location='" + location + '\'' +
				'}';
	}
}
