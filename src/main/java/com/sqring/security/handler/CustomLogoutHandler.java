package com.sqring.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * descriptions:
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    @Lazy //解决bean的循环注入
    private SessionRegistry sessionRegistry;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        // 退出之后 ，将对应session从缓存中清除 SessionRegistryImpl.principals
        sessionRegistry.removeSessionInformation(request.getSession().getId());
    }
}
