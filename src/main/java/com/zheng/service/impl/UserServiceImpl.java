package com.zheng.service.impl;

import org.springframework.stereotype.Service;

import com.zheng.domain.User;
import com.zheng.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public User getUser() {
		return new User("zhangsan", "123456", 0);
	}

}
