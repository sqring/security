package com.sqring.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * descriptions: 验证码校验异常类
 * author: Mr.zhou
 * date: 2021/5/8
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
