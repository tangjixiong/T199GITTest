package com.t199.service.impl;

import java.util.HashMap;
import java.util.List;
import com.t199.util.Contants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.t199.dao.UserDao;
import com.t199.entity.User;
import com.t199.service.UserService;
import com.t199.util.Page;
@Service("uService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	@Override
	public User login(String userCode, String pwd) {
		return dao.getUserByNameAndPwd(userCode, pwd);
	}

	@Override
	public Page getUserByPage(HashMap<String, Object> hash) {
		//统计数据总条数
		Integer totalCount=dao.getUserTotalCount(hash);
		//创建page对象
		Page page=new Page(hash.get("curpage"), hash.get("pagesize"), totalCount);
		hash.put("startrow", page.getStartrow());
		hash.put("pagesize", page.getPageSize());
		//查询指定页码数据
		List<User> list=dao.getUserByPage(hash);
		//把数据保存到page
		page.setList(list);
		return page;
	}

	@Override
	public Integer addUser(User user) {
		
		return dao.addUser(user);
	}

	@Override
	public User getUserById(Integer id) {
		return dao.getUserById(id);
	}

	@Override
	public Integer updateUser(User user) {
		
		return dao.updateUser(user);
	}

	@Override
	public User getUserByCode(String code) {
		// TODO Auto-generated method stub
		return dao.getUserByCode(code);
	}


}
