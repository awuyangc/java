package com.wy.service;

import java.util.List;

import com.wy.model.User;

public interface IUserService {
	public User getUserById(User user);
	public List<User> getUserList();
	public void insert(User user);
	public void update(User user);
	public void delete(User user);
}
