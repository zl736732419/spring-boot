package com.zheng.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by zhenglian on 2016/9/20.
 */
@Controller
public class FreemarkerController {

    @RequestMapping("/helloFtl")
    public String hello(Map<String, Object> map) {
        map.put("hello", "from Freemarker template");
        return "helloFtl";
    }

}
