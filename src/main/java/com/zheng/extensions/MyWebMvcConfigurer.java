package com.zheng.extensions;

import com.zheng.web.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 实现web配置
 * 这里实现静态资源匹配
 * 自定义拦截器配置
 * Created by zhenglian on 2016/9/20.
 */
@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/");
        registry.addResourceHandler("/app/**").addResourceLocations("classpath:/app/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加自定义拦截器
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}
