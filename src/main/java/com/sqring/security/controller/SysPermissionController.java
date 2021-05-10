package com.sqring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName SysPermissionController.java
 * @Description 角色管理
 * @createTime 2021年05月10日
 */
@Controller
@RequestMapping("/permission")
public class SysPermissionController {

    private static final String HTML_PREFIX = "system/permission/";

    @GetMapping(value = {"/", ""})
    public String permission() {
        return HTML_PREFIX + "permission-list";
    }

}