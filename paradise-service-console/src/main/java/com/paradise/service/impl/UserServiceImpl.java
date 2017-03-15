package com.paradise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paradise.mapper.UserMapper;
import com.paradise.pojo.User;
import com.paradise.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	

	/**
	 * get user by id
	 */
	@Override
	public User getUserById(int id) throws Exception{

		User user = userMapper.getUserById(id);

		return user;
	}

	/**
	 * check user exist
	 */
	@Override
	public boolean checkUserExist(User user) throws Exception {
		
		int result = userMapper.isUserExist(user);
		
		return result > 0 ? true : false;
	}

	@Override
	public int addUser(User user) throws Exception {
		
		int id = userMapper.insertUser(user);
		
		return id;
	}


}
