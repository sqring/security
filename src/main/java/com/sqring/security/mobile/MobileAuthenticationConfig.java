package com.sqring.security.mobile;

import com.sqring.security.handler.CustomAuthenticationFailureHandler;
import com.sqring.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

/**
 * descriptions: 用于组合其他关于手机登录的组件
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Component
public class MobileAuthenticationConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    UserDetailsService mobileUserDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 创建校验手机号过滤器实例
        MobileAuthenticationFilter mobileAuthenticationFilter
                = new MobileAuthenticationFilter();
        // 接收 AuthenticationManager 认证管理器
        mobileAuthenticationFilter.setAuthenticationManager(
                http.getSharedObject(AuthenticationManager.class));
        // 采用哪个成功、失败处理器
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(
                customAuthenticationSuccessHandler);
        // 指定记住我功能
        mobileAuthenticationFilter.setRememberMeServices(http.getSharedObject(RememberMeServices.class));

        mobileAuthenticationFilter.setAuthenticationFailureHandler(
                customAuthenticationFailureHandler);
        // 为 Provider 指定明确 的mobileUserDetailsService 来查询用户信息
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);
        // 将 provider 绑定到 HttpSecurity 上面，
        // 并且将 手机认证加到 用户名密码认证之后
        http.authenticationProvider(provider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
