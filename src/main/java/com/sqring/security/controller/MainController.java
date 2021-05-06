package com.sqring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName dfa.java
 * @Description TODO
 * @createTime 2021年04月30日 18:39:00
 */
@Controller
public class MainController {

    @RequestMapping({"/index", "/", ""})
    public String index() {
        return "index";
    }

}
