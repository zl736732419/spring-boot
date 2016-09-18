package com.zheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.zheng.extensions.ValuePropertySourceBean;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
@EnableConfigurationProperties(value={ValuePropertySourceBean.class})
//springBootApplication的作用就相当于前面三个配置产生的作用
public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
		
		/*
		 * 通过实例化方式启动时，设置日志打印的banner样式，由用户自定义
		 */
		SpringApplication ctx = new SpringApplication(Application.class);
//		ctx.setAdditionalProfiles("production");//代码方式设置项目运行环境为生产环境，也可以直接在配置文件中制定spring.profiles.active=production
//		ctx.setBanner(new MyBanner());
		ctx.run(args);
		
		//链式调用
//		new SpringApplicationBuilder(Application.class)
//			.banner(new MyBanner())
//			.initializers(new YamlFileApplicationContextInitializer())
//			.run(args);
	}

}
