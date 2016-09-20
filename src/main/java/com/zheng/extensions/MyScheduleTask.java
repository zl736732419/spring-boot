package com.zheng.extensions;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * springboot 定时任务
 * Created by zhenglian on 2016/9/20.
 */

//@Configuration
//@EnableScheduling
public class MyScheduleTask {
//    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduler() {
        System.out.println("hello task execute...");
    }
}
