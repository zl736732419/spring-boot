package com.zheng.extensions;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

/**
 * spring容器退出时执行的业务逻辑，
 * 有两种实现方式，
 * 一种是通过实现接口DisposableBean
 * 一种是通过添加注解@PreDestroy 见ShutdownCtxBean2
 * 模拟该场景：
 * 通过mvn package 打包项目jar, 通过命令行工具运行java -jar target/spring-boot.jar启动项目
 * 通过Ctrl+c 退出，这时可以在命令行看到打印出的退出日志
 * 
 * @author zhenglian
 */
@Component
public class ShutdownCtxBean implements DisposableBean, ExitCodeGenerator {

	@Override
	public void destroy() throws Exception {
		System.out.println("spring context has shutdown.");
	}

	@Override
	public int getExitCode() {
		return 101;
	}

}
