package com.zheng.extensions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ConfigurationProperties 
 * 用于把配置中的参数值，可以是列表注入到指定bean中，使用@Value注解不能做到这一点
 *
 * @author zhenglian
 * @data 2016年9月18日 上午10:16:30
 */
@ConfigurationProperties(prefix = "app")
@Component
public class YamlPropertySourceBean {

	private String name;
	private String age;

	private List<String> servers = new ArrayList<>();

	public List<String> getServers() {
		return servers;
	}

	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
