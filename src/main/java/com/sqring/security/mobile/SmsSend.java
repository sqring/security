package com.sqring.security.mobile;

/**
 * descriptions: 模拟发送短信验证码接口
 * author: Mr.zhou
 * date: 2021/5/8
 */
public interface SmsSend {
    boolean sendSms(String mobile, String content);
}
