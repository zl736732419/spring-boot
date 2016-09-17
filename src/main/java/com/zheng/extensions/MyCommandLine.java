package com.zheng.extensions;

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

	@Override
	public void run(String... args) throws Exception {
		System.out.println("commandline1");
		System.out.println("===============================");
	}

}
