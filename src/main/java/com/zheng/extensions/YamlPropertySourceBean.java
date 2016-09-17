package com.zheng.extensions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
