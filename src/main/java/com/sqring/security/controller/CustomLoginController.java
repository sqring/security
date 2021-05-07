package com.sqring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * descriptions: 登录处理
 * author: Mr.zhou
 * date: 2021/5/7
 */
@Controller
public class CustomLoginController {

    @RequestMapping("login/page")
    public String toLogin(){
        return "login";
    }

}
