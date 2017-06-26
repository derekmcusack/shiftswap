package com.chinaglia.service;

import com.chinaglia.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
