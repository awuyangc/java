package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.dao.UserMapper;
import com.wy.model.User;
import com.wy.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserMapper userMapper;
	@Override
	public User getUserById(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(user.getId());
	}
	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return userMapper.selectAll();
	}
	@Override
	public void insert(User user) {
		userMapper.insert(user);
		
	}
	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		userMapper.updateByPrimaryKey(user);
	}
	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userMapper.deleteByPrimaryKey(user.getId());
	}

		
}
