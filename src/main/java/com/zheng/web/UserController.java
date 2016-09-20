package com.zheng.web;

import com.zheng.domain.User;
import com.zheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/findUser/{userId}")
	@ResponseBody
	public User getUser(@PathVariable Long userId) {
		return userService.getUser(userId);
	}

	@RequestMapping("/add")
	@ResponseBody
	public String save() {
		User user = new User("zl", "123456", 0);
		userService.save(user);
		return "用户保存成功!";
	}

}
