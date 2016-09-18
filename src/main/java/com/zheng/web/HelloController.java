package com.zheng.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zheng.extensions.ProfilePropertySourceBean;
import com.zheng.extensions.ValuePropertySourceBean;
import com.zheng.extensions.YamlPropertySourceBean;

@RestController
public class HelloController {

	@Autowired
	private ValuePropertySourceBean value;

//	@Autowired
	private ProfilePropertySourceBean profileValue;
	
//	@Autowired
	private YamlPropertySourceBean yamlValue;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public String hello() {
		return "helloworld";
	}

	@RequestMapping("/value")
	@ResponseBody
	public String valueProperty() {
		return value.getName() + "," + value.getAge() + value.getServers();
	}

	@RequestMapping("/yaml_value")
	@ResponseBody
	public String yamlValueProperty() {
		return yamlValue.getName() + "," + yamlValue.getAge() + yamlValue.getServers();
	}
	
	@RequestMapping("/profile_value")
	@ResponseBody
	public String profileValueProperty() {
		return profileValue.getAddress();
	}
}
