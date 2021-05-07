package com.sqring.security.handler;

import com.alibaba.fastjson.JSON;
import com.sqring.security.properties.LoginResponseType;
import com.sqring.security.properties.SecurityProperties;
import com.sqring.security.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
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
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 认证成功后处理逻辑
     *
     * @param authentication 封装了用户信息 UserDetails，访问IP等  
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(
                securityProperties.getAuthentication().getLoginType())) {
            // 认证成功后，响应JSON字符串
            R result = R.ok("认证成功");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        } else {
            //重定向到上次请求的地址上，引发跳转到认证页面的地址
            logger.info("authentication: " + JSON.toJSONString(authentication));
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
