package com.sqring.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqring.security.entities.SysPermission;
import com.sqring.security.mapper.SysPermissionMapper;
import com.sqring.security.service.SysPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName SysPermissionServiceImpl.java
 * @Description TODO
 * @createTime 2021年05月11日
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {


    @Override
    public List<SysPermission> findByUserId(Long userId) {
        if(userId == null) {
            return null;
        }
        List<SysPermission> permissionList = baseMapper.selectPermissionByUserId(userId);
        // 如果没有权限，则将集合中的数据null移除
//        permissionList.remove(null);
        return permissionList;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        // 1. 删除当前id的权限
        baseMapper.deleteById(id);
        // 2. 删除parent_id = id 的权限, 删除当前点击的子权限
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper();
        //delete from sys_permission where parent_id = #{id};
        queryWrapper.eq(SysPermission::getParentId, id);
        baseMapper.delete(queryWrapper);
        return true;
    }

}