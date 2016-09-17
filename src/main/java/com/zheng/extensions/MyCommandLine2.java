package com.zheng.extensions;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=0)
public class MyCommandLine2 implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("CommandLine2");
		System.out.println("---------------");
	}

}
