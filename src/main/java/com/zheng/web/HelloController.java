package com.zheng.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zheng.extensions.ProfilePropertySourceBean;
import com.zheng.extensions.ValuePropertySourceBean;
import com.zheng.extensions.YamlPropertySourceBean;
/**
 * 直接使用@RestController标识请求返回的是json格式数据
 * 所以我们无需再添加@ResponseBody注解标签
 *
 * @author zhenglian
 * @data 2016年9月18日 下午5:40:20
 */
@RestController
public class HelloController {

//	@Autowired
	private ValuePropertySourceBean value;

	@Autowired
	private ProfilePropertySourceBean profileValue;
	
//	@Autowired
	private YamlPropertySourceBean yamlValue;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "helloworld";
	}

	@RequestMapping("/value")
	public String valueProperty() {
		return value.getName() + "," + value.getAge() + value.getServers();
	}

	@RequestMapping("/yaml_value")
	public String yamlValueProperty() {
		return yamlValue.getName() + "," + yamlValue.getAge() + yamlValue.getServers();
	}
	
	@RequestMapping("/profile_value")
	public String profileValueProperty() {
		return profileValue.getAddress();
	}
}
