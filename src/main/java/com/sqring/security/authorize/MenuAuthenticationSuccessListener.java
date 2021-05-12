package com.sqring.security.authorize;

import cn.hutool.core.collection.CollUtil;
import com.sqring.security.entities.SysPermission;
import com.sqring.security.entities.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Lists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName MenuAuthenticationSuccessListener.java
 * @Description 认证成功监听器
 * @createTime 2021年05月12日
 */
@Component
public class MenuAuthenticationSuccessListener implements AuthenticationSuccessListener {
    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 查询用户所拥有的权限菜单
     * @param request
     * @param response
     * @param authentication 当用户认证通过后，会将认证对象传入
     */
    @Override
    public void successListener(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logger.info("查询用户所拥有的权限菜单: " + authentication);
        Object principal = authentication.getPrincipal();
        if(principal != null && principal instanceof SysUser) {
            SysUser sysUser = (SysUser)principal;
            loadMenuTree(sysUser);
        }

        Object newPrincipal = authentication.getPrincipal();
        // #authentication.principal.permissions
        logger.info("newPrincipal: " + newPrincipal);
    }

    /**
     * 只加载菜单 ，不需要按钮
     * @param sysUser
     */
    public void loadMenuTree(SysUser sysUser) {
        // 获取到了当前登录用户的菜单 和按钮
        List<SysPermission> permissions = sysUser.getPermissions();
        if(CollUtil.isEmpty(permissions)) {
            return;
        }

        // 1. 获取权限集合中所有的菜单 ，不要按钮
        List<SysPermission> menuList = CollUtil.newArrayList();
        for(SysPermission p: permissions) {
            if(p.getType().equals(1)) {
                menuList.add(p);
            }
        }

        // 2. 获取一下每个菜单 的子菜单
        for(SysPermission menu: menuList) {
            // 存放当前菜单的所有子菜单
            List<SysPermission> childMenu = CollUtil.newArrayList();
            List<String> childUrl = CollUtil.newArrayList();
            // 获取menu菜单下的所有子菜单
            for(SysPermission p: menuList) {
                // 如果p.parentId等于menu.id则就是它的子菜单
                if(p.getParentId().equals(menu.getId())) {
                    childMenu.add(p);
                    childUrl.add(p.getUrl());
                }
            }
            // 设置子菜单
            menu.setChildren(childMenu);
            menu.setChildrenUrl(childUrl);
        }

        // 3. menuList 里面每个SysPermission都有一个 子菜单Children集合
        // 首页》Children没有元素， 系统管理SysPermission》Children有3个元素（用户，角色，权限）
        List<SysPermission> result = CollUtil.newArrayList();
        for(SysPermission menu: menuList) {
            // 如果父id是0，则根菜单
            if(menu.getParentId().equals(0L)) {
                result.add(menu);
            }
        }
        // 最终把获取的根菜单（子菜单集合）重新设置到permission集合中
        sysUser.setPermissions(result);

    }

}