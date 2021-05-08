package com.sqring.security.controller;

import cn.hutool.core.util.RandomUtil;
import com.sqring.security.mobile.SmsSend;
import com.sqring.security.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * descriptions: 关于手机登录控制层
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Controller
public class MobileLoginController {

    public static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";

    /**
     * Description: 前往手机登录页面
     * Params: []
     * Returns: java.lang.String
     */
    @RequestMapping("/mobile/page")
    public String toMobilePage() {
        return "login-mobile";
    }

    @Autowired
    private SmsSend smsSend;

    @ResponseBody //响应json字符串
    @RequestMapping("/code/mobile")
    public R smsCode(HttpServletRequest request) {
        // 1. 生成一个手机验证码
        String code = RandomUtil.randomNumbers(4);
        // 2. 将手机验证码保存到session中
        request.getSession().setAttribute(SESSION_KEY, code);
        // 3. 发送验证码到用户手机上
        String mobile = request.getParameter("mobile");
        smsSend.sendSms(mobile, code);
        return R.ok();
    }
}
