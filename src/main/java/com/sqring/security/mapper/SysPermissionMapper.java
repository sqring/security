package com.sqring.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqring.security.entities.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName SysPermissionMapper.java
 * @Description TODO
 * @createTime 2021年05月11日
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {


    List<SysPermission> selectPermissionByUserId(@Param("userId") Long userId);

    List<SysPermission> findByRoleId(@Param("roleId") Long roleId);


}