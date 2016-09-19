package com.zheng.service;

import com.zheng.domain.User;

public interface UserService {
	
	public User getUser(Long userId);

	public void save(User user);
	
}
