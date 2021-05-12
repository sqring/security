package com.sqring.security.entities;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther:  www.zhouwenfang.com
 */
@Data
public class SysRole  implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String remark;

    private Date createDate;
    private Date updateDate;

    /**
     * 存储当前角色的权限资源对象集合
     * 修改角色时用到
     */
    @TableField(exist = false)
    private List<SysPermission> perList = CollUtil.newArrayList();
    /**
     * 存储当前角色的权限资源ID集合
     * 修改角色时用到
     */
    @TableField(exist = false)
    private List<Long> perIds = CollUtil.newArrayList();

    public List<Long> getPerIds() {
        if(CollUtil.isNotEmpty(perList)) {
            perIds = CollUtil.newArrayList();
            for(SysPermission per : perList) {
                perIds.add(per.getId());
            }
        }
        return perIds;
    }
}
