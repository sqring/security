package com.sqring.security.handler;

import com.sqring.security.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * descriptions: 认证失败处理器
 * author: Mr.zhou
 * date: 2021/5/7
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 认证失败状态码 401
        R result = R.build(
                HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result.toJsonString());
    }
}
