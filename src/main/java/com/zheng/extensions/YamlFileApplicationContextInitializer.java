package com.zheng.extensions;

import java.io.IOException;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

/**
 * 这里通过使用YamlPropertySourceLoader将yml文件中的属性映射到properties中，在程序中通过@Value来引用
 * 需要在Application.java启动时加载
 * @author zhenglian
 * @data 2016年9月18日 上午10:04:08
 */
public class YamlFileApplicationContextInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext ctx) {
		try {
			Resource resource = ctx.getResource("classpath:application.yml");
			YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
			PropertySource<?> source = loader.load("yamlToProp", resource, null);
			ctx.getEnvironment().getPropertySources().addFirst(source);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
