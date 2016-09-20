package com.zheng.extensions;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by zhenglian on 2016/9/20.
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtils.ctx == null) {
            SpringUtils.ctx = applicationContext;
        }

        System.out.println("applicationContext已经注册，可以在普通类中获取容器中的bean了...");
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    public Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    public Object getBean(Class beanCls) {
        return ctx.getBean(beanCls);
    }

    public Object getBean(String name, Class beanCls) {
        return ctx.getBean(name, beanCls);
    }
}
