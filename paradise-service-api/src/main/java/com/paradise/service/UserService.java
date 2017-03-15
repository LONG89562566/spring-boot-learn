package com.paradise.service;


import com.paradise.pojo.User;


public interface UserService {
	
	public User getUserById(int id) throws Exception;
	
	public boolean checkUserExist(User user) throws Exception;
	
	public int addUser(User user) throws Exception;
	
}
