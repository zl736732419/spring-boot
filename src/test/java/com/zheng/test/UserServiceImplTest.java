package com.zheng.test;

import com.zheng.domain.User;
import com.zheng.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhenglian on 2016/9/23.
 */
public class UserServiceImplTest extends BaseServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUser() {
        User user = userService.getUser(1L);
        System.out.println(user);
    }

}
