package com.fanlinchong.springframework.test.bean;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
	public final List<User> userList = new ArrayList<User>() {{
		add(new User(10001L, "zhang san"));
		add(new User(10002L, "li si"));
		add(new User(10003L, "wang wu"));
		add(new User(10004L, "zhao liu"));
	}};

	@Override
	public User getUser(Long userId) {
		for (User user : userList) {
			if (user.getId().equals(userId)) {
				return user;
			}
		}

		return null;
	}

	@Override
	public void registerUser(User user) {
		userList.add(user);
	}

	@Override
	public int count() {
		return userList.size();
	}
}
