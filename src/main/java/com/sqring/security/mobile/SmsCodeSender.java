package com.sqring.security.mobile;

import lombok.extern.slf4j.Slf4j;

/**
 * descriptions: 发送短信验证码的实现
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Slf4j
public class SmsCodeSender implements SmsSend {
    @Override
    public boolean sendSms(String mobile, String content) {
        String sendContent = String.format("验证码%s，请勿泄露他人。", content);
        log.info("向手机号" + mobile + "发送的短信为:" + sendContent);
        return true;
    }
}
