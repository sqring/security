package com.sqring.security.service;

import com.sqring.security.entities.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * descriptions: 通过手机号获取用户信息和权限资源
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Component("mobileUserDetailsService")
@Slf4j
public class MobileUserDetailsService extends AbstractUserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public SysUser findSysUser(String usernameOrMobile) {
        log.info("请求的手机号是：" + usernameOrMobile);
        // 1. 通过手机号查询用户信息
        return sysUserService.findByMobile(usernameOrMobile);
    }
}
