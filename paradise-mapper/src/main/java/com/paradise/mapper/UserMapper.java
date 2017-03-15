package com.paradise.mapper;

import com.paradise.pojo.User;

public interface UserMapper {

	public User getUserById(int id) throws Exception;
	
	public int isUserExist(User user) throws Exception;
	
	public int insertUser(User user) throws Exception;
	
}
