package com.t199.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.t199.entity.Role;
import com.t199.entity.User;

public interface UserDao {
	public User getUserByNameAndPwd(@Param("loginid")String loginid,
			@Param("pwd")String pwd);
	//分页功能
	//统计数据总条数
	public Integer getUserTotalCount(Map<String,Object> hash);
	//查询指定页码的数据
	public List<User> getUserByPage(Map<String,Object> hash);//limit 起始位置，结束位置
	
	public Integer addUser(User user);
	
	//根据id查询用户信息
	public User getUserById(Integer id);
	
	public Integer updateUser(User user);
	//根据usercode查询信息
	public User getUserByCode(String code);
	
}
