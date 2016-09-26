package com.zheng.test;

import com.zheng.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by zhenglian on 2016/9/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//由于是WEB项目，所以Junit需要模拟ServletContext, 因此加上WEB环境
@WebAppConfiguration
public class BaseServiceTest {
}
