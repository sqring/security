package com.sqring.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqring.security.entities.SysRole;
import com.sqring.security.entities.SysUser;
import com.sqring.security.mapper.SysRoleMapper;
import com.sqring.security.mapper.SysUserMapper;
import com.sqring.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName SysUserServiceImpl.java
 * @Description TODO
 * @createTime 2021年05月11日
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final String PASSWORD_DEFAULT = "$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm";
    @Override
    public SysUser findByUsername(String username) {
        if(StrUtil.isEmpty(username)) {
            return null;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        // baseMapper 对应的是就是 SysUserMapper
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser findByMobile(String mobile) {
        if(StrUtil.isEmpty(mobile)) {
            return null;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile", mobile);
        // baseMapper 对应的是就是 SysUserMapper
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser) {
        return baseMapper.selectPage(page, sysUser);
    }


    @Autowired
    SysRoleMapper sysRoleMapper;
    /**
     * 1. 用户id查询用户信息 sys_user
     * 2. 用户id查询所拥有的角色
     * @param id
     * @return
     */
    @Override
    public SysUser findById(Long id) {

        if(id == null) {
            return new SysUser();
        }

        // 1. 用户id查询用户信息 sys_user
        SysUser sysUser = baseMapper.selectById(id);
        // 2. 用户id查询所拥有的角色
        List<SysRole> roleList = sysRoleMapper.findByUserId(id);
        sysUser.setRoleList(roleList);
        return sysUser;
    }



    @Transactional //开启事务
    @Override
    public boolean saveOrUpdate(SysUser entity) {
        if(entity != null && entity.getId() == null) {
            //新增设置默认密码1234
            entity.setPassword(PASSWORD_DEFAULT);
        }
        entity.setUpdateDate(new Date());
        // 1.更新或者保存操作
        boolean flag = super.saveOrUpdate(entity);

        if(flag) {
            // 2. 先删除用户角色表中的数据
            baseMapper.deleteUserRoleByUserId(entity.getId());
            // 3. 再新增到用户角色表中
            if(CollectionUtils.isNotEmpty(entity.getRoleIds())) {
                baseMapper.saveUserRole(entity.getId(), entity.getRoleIds());
            }
        }

        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        // 1. 查询用户信息
        SysUser sysUser = baseMapper.selectById(id);
        // 2. 再更新用户信息，将状态更新为已删除
        sysUser.setEnabled(false);
        sysUser.setUpdateDate(new Date());
        baseMapper.updateById(sysUser);
        return true;
    }
}