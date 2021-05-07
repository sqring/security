package com.sqring.security.handler;

import com.sqring.security.utils.R;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * descriptions: 认证成功处理器
 * author: Mr.zhou
 * date: 2021/5/7
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     *   * 认证成功后处理逻辑
     *   * @param authentication 封装了用户信息 UserDetails，访问IP等
     *   
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 当认证成功后，响应 JSON 数据给前端
        R result = R.ok("认证成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result.toJsonString());
    }
}
