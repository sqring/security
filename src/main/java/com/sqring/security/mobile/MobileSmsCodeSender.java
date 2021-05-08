package com.sqring.security.mobile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * descriptions: 替换SmsSend.class的默认实现
 * author: Mr.zhou
 * date: 2021/5/8
 */
// @Component
@Slf4j
public class MobileSmsCodeSender implements SmsSend {


    @Override
    public boolean sendSms(String mobile, String content) {
        log.info("Web应用新的短信验证码接口---向手机号" + mobile + "发送了验证码为：" + content);
        return false;
    }
}
