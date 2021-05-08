package com.sqring.security.session;

import com.sqring.security.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * descriptions: session失效后的处理逻辑
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Slf4j
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {

    private SessionRegistry sessionRegistry;

    public CustomInvalidSessionStrategy(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("getSession().getId(): " + request.getSession().getId());
        log.info("getRequestedSessionId(): " + request.getRequestedSessionId());
        sessionRegistry.removeSessionInformation(request.getRequestedSessionId());

        // 要将浏览器中的cookie的jsessionid删除
        cancelCookie(request, response);

        R result = new R().build(
                HttpStatus.UNAUTHORIZED.value(), "登录已超时，请重新登录");

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result.toJsonString());
    }

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
