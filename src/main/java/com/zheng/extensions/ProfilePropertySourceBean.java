package com.zheng.extensions;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 通过Profile标识当前配置需要的运行环境，
 * 运行环境可以在配置文件中进行配置，可以查看application.yml中的配置
 * 需要制定spring.profiles.active属性为指定的运行环境
 * 
 * 如果没有运行在指定的项目环境下，则会报错
 *
 * @author zhenglian
 * @data 2016年9月18日 上午11:17:10
 */
@Component
@Profile(value={"production", "development"})
@ConfigurationProperties(prefix = "app")
public class ProfilePropertySourceBean {
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
