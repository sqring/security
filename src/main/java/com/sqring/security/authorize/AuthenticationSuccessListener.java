package com.sqring.security.authorize;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName AuthenticationSuccessListener.java
 * @Description 这个接口是用来监听认证成功之后的处理，也就是说认证成功让成功处理器调用此接口方法 successListener
 * @createTime 2021年05月12日
 */
public interface AuthenticationSuccessListener {

    void successListener(HttpServletRequest request,
                         HttpServletResponse response, Authentication authentication);
}