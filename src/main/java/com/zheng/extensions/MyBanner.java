package com.zheng.extensions;

import java.io.PrintStream;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

/**
 * 自定义实现spring项目启动时显示的banner,默认的banner是由字符组成的spring图案
 * 如果要关闭banner，可以使用下面的实例化对象.setShowBanner(false);关闭
 * 还有一种简单的配置方式：
 * 在classpath目录下新建banner.txt文件
 * 在其中定义banner要输出的版本内容，可以引用的变量为：
 * ${application.version}
 * ${application.formatted-version}
 * ${spring-boot.version}
 * ${spring-boot.formatted-version}
 * 
 * @author zhenglian
 */
public class MyBanner implements Banner {

	@Override
	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
		out.println("welcome to spring boot...");
	}

}
