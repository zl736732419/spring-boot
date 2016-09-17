package com.zheng.extensions;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class ShutdownCtxBean2 {

	@PreDestroy
	public void destroy() {
		System.out.println("shutdown spring context ..........");
		System.out.println("----------------------------------");
	}
	
}
