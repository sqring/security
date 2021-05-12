package com.sqring.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqring.security.entities.SysPermission;

import java.util.List;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName SysPermissionService.java
 * @Description TODO
 * @createTime 2021年05月11日
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 通过用户id查询所拥有权限
     * @param userId
     * @return
     */
    List<SysPermission> findByUserId(Long userId);


    /**
     * 通过权限id删除权限资源
     * @param id
     * @return
     */
    boolean deleteById(Long id);

}