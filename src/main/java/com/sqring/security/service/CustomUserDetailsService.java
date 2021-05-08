package com.sqring.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * descriptions:
 * author: Mr.zhou
 * date: 2021/5/7
 */
@Component("customUserDetailsService")
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("请求认证的用户名：{}", username);
        if (!"zwf".equalsIgnoreCase(username)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 如果有此用户信息, 假设数据库查询到的用户密码是 1234
        String password = passwordEncoder.encode("1234");
        // 2.查询用户拥有权限
        // 3.封装用户信息: username用户名,password数据库中的密码,authorities资源权限标识符
        // SpringSecurity 底层会校验是否身份合法。
        return new User(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
