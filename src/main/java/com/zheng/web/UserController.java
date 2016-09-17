package com.zheng.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zheng.domain.User;
import com.zheng.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/findUser/{userId}")
	@ResponseBody
	public User getUser(@PathVariable Long userId) {
		User user = userService.getUser();
		user.setId(userId);
		return user;
	}

}
