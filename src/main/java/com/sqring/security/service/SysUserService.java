package com.sqring.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqring.security.entities.SysUser;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName SysUserService.java
 * @Description TODO
 * @createTime 2021年05月11日
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     * @return
     */
    SysUser findByUsername(String username) ;

    /**
     * 通过手机号查询用户信息
     * @param mobile 手机号
     * @return
     */
    SysUser findByMobile(String mobile);

    /**
     * 分页查询用户信息
     * @param page 分页对象
     * @param sysUser 查询条件
     * @return
     */
    IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser);

    /**
     * 1. 用户id查询用户信息 sys_user
     * 2. 用户id查询所拥有的角色
     * @param id
     * @return
     */
    SysUser findById(Long id);

    /**
     * 通过用户id来假删除， 将is_enabled = 0
     * @param id
     * @return
     */
    boolean deleteById(Long id);
}