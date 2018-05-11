package com.t199.service;

import java.util.HashMap;

import com.t199.entity.User;
import com.t199.util.Page;

public interface UserService {
	public User login(String userCode,String pwd);
	
	public Page getUserByPage(HashMap<String, Object> hash);
	
	public Integer addUser(User user);
	
	//根据id查询用户信息
		public User getUserById(Integer id);
		
		public Integer updateUser(User user);
		//根据usercode查询信息
		public User getUserByCode(String code);
}
