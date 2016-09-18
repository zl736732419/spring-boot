package com.zheng.extensions;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Lists;

/**
 * 值覆盖顺序 命令行参数 --prop=value 如果有多个，则直接以空格分割 --name='zhangsan' --age=20
 * 1.来自于java:comp/env的JNDI属性 2.Java系统属性（System.getProperties()） 3.操作系统环境变量
 * 4.只有在random.*里包含的属性会产生一个RandomValuePropertySource ${random.value}${random.*}
 * 5.在打包的jar外的应用程序配置文件（application.properties，包含YAML和profile变量）
 * 6.在打包的jar内的应用程序配置文件（application.properties，包含YAML和profile变量）
 * 上面两点还可以放置在config目录下，也就是classpath/config/, jar所在目录/config/,config优先级要高
 * 可以通过参数重新指定位置和文件名： --spring.config.name=myproject
 * --spring.config.location=classpath:/default.properties
 * 需要注意的是，在配置jar外application.properties时，需要cmd到jar所在的目录下，不然配置不生效
 * 7.在@Configuration类上的@PropertySource注解
 * 8.默认属性（使用SpringApplication.setDefaultProperties指定）
 * 
 * 需要注意的是，如果这里不使用@Component注解将其注册,那么可以在Application.
 * java中通过@EnableConfigurationProperties({ValuePropertySourceBean.class})进行注册
 * 
 * @author zhenglian
 */
@ConfigurationProperties(prefix = "app")
// @Component
public class ValuePropertySourceBean {

	// @Value("${app.name}")
	private String name;

	// @Value("${app.age}")
	private Integer age;

	// @Value("${app.servers}")
	private List<String> servers = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<String> getServers() {
		return servers;
	}

}
