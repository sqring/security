package com.sqring.security.filter;

import cn.hutool.core.util.StrUtil;
import com.sqring.security.controller.MobileLoginController;
import com.sqring.security.exception.ValidateCodeException;
import com.sqring.security.handler.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * descriptions:  校验用户输入的手机验证码是否正确
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Component
public class MobileValidateFilter extends OncePerRequestFilter {

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/mobile/form".equals(request.getRequestURI())
                && "post".equalsIgnoreCase(request.getMethod())) {
            try {
            // 校验验证码合法性
                validate(request);
            } catch (AuthenticationException e) {
            // 交给失败处理器进行处理异常
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
            // 一定要记得结束
                return;
            }
        }
            // 非手机验证码登录，则直接放行
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        // 先获取seesion中的验证码
        String sessionCode =
                (String) request.getSession().getAttribute(MobileLoginController.SESSION_KEY);
        // 获取用户输入的验证码
        String inpuCode = request.getParameter("code");
        // 判断是否正确
        if (StrUtil.isBlank(inpuCode)) {
            throw new ValidateCodeException("短信验证码不能为空");
        }
        if (!inpuCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("短信验证码输入错误");
        }

    }
}
