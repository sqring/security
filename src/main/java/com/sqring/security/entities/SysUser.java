package com.sqring.security.entities;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Auther:  www.zhouwenfang.com
 */
@Data
public class SysUser implements UserDetails {

    @TableId(type = IdType.AUTO) // 表示主键自增长
    private Long id;

    private String username;
    /**
     * 密码需要通过加密后存储
     */
    private String password;
    /**
     * 帐户是否有效：1 未过期，0已过期
     * 1 true
     * 0 false
     */
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    /**
     * 它不是sys_user表中的属性，所以要进行标识，不然mybatis-plus会报错
     */
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;

    private String nickName;
    private String mobile;
    private String email;
    private Date createDate;
    private Date updateDate;


    /**
     * 拥有角色集合
     */
    @TableField(exist = false)
    private List<SysRole> roleList = CollUtil.newArrayList();
    /**
     * 获取所有角色id
     */
    @TableField(exist = false)
    private List<Long> roleIds = CollUtil.newArrayList();
    public List<Long> getRoleIds() {
        if(CollUtil.isNotEmpty(roleList)) {
            roleIds = CollUtil.newArrayList();
            for(SysRole role : roleList) {
                roleIds.add(role.getId());
            }
        }
        return roleIds;
    }

    /**
     * 封装当前用户拥有的权限资源对象
     */
    @TableField(exist = false)
    private List<SysPermission> permissions = CollUtil.newArrayList();


}
