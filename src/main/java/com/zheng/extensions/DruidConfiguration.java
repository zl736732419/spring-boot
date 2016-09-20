package com.zheng.extensions;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhenglian on 2016/9/20.
 */
@Configuration
public class DruidConfiguration {

    @Bean
    public ServletRegistrationBean druidServletRegistryBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid2/*");
        //添加初始化参数

        //白名单
        bean.addInitParameter("allow", "127.0.0.1");
        bean.addInitParameter("deny", "192.168.1.116");
        bean.addInitParameter("loginUsername", "admin");
        bean.addInitParameter("loginPassword", "123456");
        bean.addInitParameter("resetEnable", "false");

        return bean;
    }

    @Bean
    public FilterRegistrationBean druidFilterRegistryBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则
        bean.addUrlPatterns("/*");
        bean.addInitParameter("exclusions", "*.js,*.css,*.bmp,*.png,*.gif,*.jpg,*.ico,/druid2/*");

        return bean;
    }
}
