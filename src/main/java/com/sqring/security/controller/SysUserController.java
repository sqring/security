package com.sqring.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqring.security.entities.SysRole;
import com.sqring.security.entities.SysUser;
import com.sqring.security.service.SysRoleService;
import com.sqring.security.service.SysUserService;
import com.sqring.security.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName SysUserController.java
 * @Description 用户管理
 * @createTime 2021年05月10日
 */
@Controller
@RequestMapping("/user")
public class SysUserController {

    private static final String HTML_PREFIX = "system/user/";


    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping(value = {"/", ""}) // /user/  /user
    public String user() {
        return HTML_PREFIX + "user-list";
    }

    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询用户列表
     * @param page    分页对象: size, current
     * @param sysUser 查询条件 : username, mobile
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:list')")
    @PostMapping("/page") // /user/page
    @ResponseBody
    public R page(Page<SysUser> page, SysUser sysUser) {
        return R.ok(sysUserService.selectPage(page, sysUser));
    }

    @Autowired
    private SysRoleService sysRoleService;

    @PreAuthorize("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model) {
        // 1, 查询用户信息，包含了用户所拥有的角色
        SysUser user = sysUserService.findById(id);
        model.addAttribute("user", user);

        // 2, 查询出所有角色信息 sys_role
        List<SysRole> roleList = sysRoleService.list();
        model.addAttribute("roleList", roleList);

        return HTML_PREFIX + "user-form";
    }

    @PreAuthorize("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "")
    public String saveOrUpdate(SysUser sysUser) {
        // 1. 保存到用户表, 要将选择的角色保存到用户角色中间表
        sysUserService.saveOrUpdate(sysUser);
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/{id}") // /user/{id}
    public R deleteById(@PathVariable Long id) {
        // 假删除，只做更新
        sysUserService.deleteById(id);
        return R.ok();
    }
}