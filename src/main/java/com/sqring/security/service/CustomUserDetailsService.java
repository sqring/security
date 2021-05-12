package com.sqring.security.service;

import com.sqring.security.entities.SysUser;
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
public class CustomUserDetailsService extends AbstractUserDetailsService{

    @Autowired // 不能删掉，不然报错
    PasswordEncoder passwordEncoder;

    @Autowired
    SysUserService sysUserService;

    @Override
    public SysUser findSysUser(String usernameOrMobile) {
        log.info("请求认证的用户名: " + usernameOrMobile);
        // 1. 通过请求的用户名去数据库中查询用户信息
        return sysUserService.findByUsername(usernameOrMobile);
    }
}
