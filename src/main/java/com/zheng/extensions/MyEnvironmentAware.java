package com.zheng.extensions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 获取环境变量中的值和application.properties中配置的参数
 * Created by zhenglian on 2016/9/21.
 */
@Configuration
public class MyEnvironmentAware implements EnvironmentAware {

    @Value("${spring.datasource.url}")
    private String url;

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("Environment获取springboot中配置的参数信息.....");
        System.out.println(url);

        //通过Environment获取环境变量参数
        System.out.println(environment.getProperty("JAVA_HOME"));
        
        //获取application.properties中配置的参数
        System.out.println(environment.getProperty("spring.datasource.username"));

        //获取前缀是spring.datasource的配置参数
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "spring.datasource.");

        System.out.println("获取前缀为spring.datasource的参数...");
        System.out.println(resolver.getProperty("username") + ":" + resolver.getProperty("password"));

        
    }
}
