package com.zheng.service.impl;

import com.zheng.domain.User;
import com.zheng.repository.UserRepository;
import com.zheng.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User getUser(Long userId) {
        System.out.println("获取用户数据...");
        return userRepository.findOne(userId);
    }

    @Override
    public void save(User user) {
        System.out.println("用户保存成功！");
        userRepository.save(user);
    }
}
