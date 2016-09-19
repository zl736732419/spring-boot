package com.zheng;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.base.Charsets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
//springBootApplication的作用就相当于前面三个配置产生的作用
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.zheng.repository")
//@EnableConfigurationProperties(value={ValuePropertySourceBean.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

		/*
		 * 通过实例化方式启动时，设置日志打印的banner样式，由用户自定义
		 */
//		SpringApplication ctx = new SpringApplication(Application.class);
//		ctx.setAdditionalProfiles("production");//代码方式设置项目运行环境为生产环境，也可以直接在配置文件中制定spring.profiles.active=production
//		ctx.setBanner(new MyBanner());
//		ctx.run(args);

        //链式调用
//		new SpringApplicationBuilder(Application.class)
//			.banner(new MyBanner())
//			.initializers(new YamlFileApplicationContextInitializer())
//			.run(args);
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(Charsets.UTF_8);
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        converter.setFastJsonConfig(config);

        return new HttpMessageConverters(converter);

    }

}
