package com.zheng.extensions;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by zhenglian on 2016/9/23.
 */
@Component
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int statusCode = check();
        if(statusCode > 0) {
            return Health.down().withDetail("Error Code", statusCode).build();
        }else {
            return Health.up().build();
        }
    }

    /**
     * 实现一些健康状态检查
     * @return >0标识错误，=0标识正常
     */
    private int check() {
        //TODO add check logical code ...
        return 500;
    }
}
