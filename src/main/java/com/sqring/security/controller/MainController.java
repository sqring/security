package com.sqring.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author Mr.zhou
 * @version 1.0.0
 * @ClassName MainController.java
 * @Description 获取用户信息
 * @createTime 2021年04月30日 18:39:00
 */
@Controller
public class MainController {

    @RequestMapping({"/index", "/", ""})
    public String index(Map<String, Object> map) {
        // 第1方式：
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails)principal;
            String username = userDetails.getUsername();
            map.put("username", username);
        }
        return "index";
    }


    @RequestMapping("/user/info")
    @ResponseBody
    public Object userInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }

    @RequestMapping("/user/info2")
    @ResponseBody
    public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

}
