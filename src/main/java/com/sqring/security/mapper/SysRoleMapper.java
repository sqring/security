package com.sqring.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqring.security.entities.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName SysRoleMapper.java
 * @Description TODO
 * @createTime 2021年05月11日
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * 只要将非 limit 的sql 语句写在 对应的 id="selectPage"里面（SysRoleMapper.xml），不需要自动去分页，而mybaits-plus会自动实现分页
     * 但是，它实现分页，你需要第一个参数会传入Page，而其他参数你需要使用@Param(xx)要取别名
     * 最终查询到的数据会被封装到IPage实现里面
     * @param page
     * @param sysRole
     * @return
     */
    IPage<SysRole> selectPage(Page<SysRole> page, @Param("p") SysRole sysRole);


    /**
     * 通过角色id删除角色权限表中的所有记录
     * @param roleId
     * @return
     */
    boolean deleteRolePermissionByRoleId(@Param("roleId") Long roleId);

    /**
     * 保存角色 与权限关系表数据
     * @param roleId 角色id
     * @param perIds 角色所拥有的权限
     * @return
     */
    boolean saveRolePermission(@Param("roleId") Long roleId,@Param("perIds") List<Long> perIds);

    /**
     * 通过用户id查询所拥有的角色
     * @param userId 用户id
     * @return 查询到信息集合
     */
    List<SysRole> findByUserId(@Param("userId")Long userId);
}