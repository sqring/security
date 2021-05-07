package com.sqring.security.handler;

import com.sqring.security.properties.LoginResponseType;
import com.sqring.security.properties.SecurityProperties;
import com.sqring.security.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(
                securityProperties.getAuthentication().getLoginType())) {
            // 认证失败响应JSON字符串，
            R result = R.build(HttpStatus.UNAUTHORIZED.value(),
                    exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        } else {
            // 重写向回认证页面，注意加上 ?error
            super.setDefaultFailureUrl(
                    securityProperties.getAuthentication().getLoginPage() + "?error");
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
