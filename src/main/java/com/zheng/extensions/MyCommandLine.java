package com.zheng.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 当spring容器启动之后执行的一些特定的代码
 * 
 * 
 * @author zhenglian
 */
@Component
@Order(value=1)
public class MyCommandLine implements CommandLineRunner {

	@Autowired
	private MyConfigProp prop;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("commandline1");
		System.out.println("===============================");
		System.out.println("从自定义配置文件中获取配置参数信息........");
		System.out.println(prop.getUrl());
		System.out.println(prop.getUsername());
		System.out.println(prop.getPassword());
		System.out.println(prop.getDriverClassName());
	}

}
