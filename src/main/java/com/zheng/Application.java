package com.zheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
//springBootApplication的作用就相当于前面三个配置产生的作用
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		/*
		 * 通过实例化方式启动时，设置日志打印的banner样式，由用户自定义
		 */
//		SpringApplication ctx = new SpringApplication(Application.class);
//		ctx.setBanner(new MyBanner());
//		ctx.run(args);
		
		//链式调用
//		new SpringApplicationBuilder(Application.class)
//			.banner(new MyBanner())
//			.run(args);
		
		
	}

}
