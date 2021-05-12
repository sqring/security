package com.sqring.security.controller;

import com.sqring.security.entities.SysPermission;
import com.sqring.security.service.SysPermissionService;
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
 * @ClassName SysPermissionController.java
 * @Description 角色管理
 * @createTime 2021年05月10日
 */
@Controller
@RequestMapping("/permission")
public class SysPermissionController {

    private static final String HTML_PREFIX = "system/permission/";

    @PreAuthorize("hasAuthority('sys:permission')")
    @GetMapping(value = {"/", ""}) // /permission/  /permission
    public String permission() {
        return HTML_PREFIX + "permission-list";
    }

    @Autowired
    private SysPermissionService sysPermissionService;

    @PreAuthorize("hasAuthority('sys:permission:list')")
    @PostMapping("/list") // /permission/list
    @ResponseBody // 不要少了,返回json
    public R list() {
        // MyBatis-plus已经提供的,查询SysPermission表中的所有记录
        List<SysPermission> list = sysPermissionService.list();
        return R.ok(list);
    }


    /**
     * 跳转新增或者修改页面
     *  /form 新增
     *  /form/{id} 修改
     *
     *  @PathVariable(required = false) 设置为false，则id可传也可不传，不然报500
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:permission:edit', 'sys:permission:add')")
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model) { // Map<String,Object>
//        System.out.println("id:" + id);
        // 1. 通过权限id查询对应权限信息
        SysPermission permission = sysPermissionService.getById(id);
//        SysPermission permission2 = sysPermissionService.getById(permission.getParentId());
//        permission.setParentName(permission2.getName());
        // 绑定后页面可获取
        model.addAttribute("permission", permission == null ? new SysPermission() : permission);

        return HTML_PREFIX + "permission-form";
    }

    /**
     * 这个是提交新增或修改的数据,
     * @param permission
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:permission:edit', 'sys:permission:add')")
    @RequestMapping(value="", method = {RequestMethod.PUT, RequestMethod.POST}) // /permission
    public String saveOrUpdate(SysPermission permission) {
        sysPermissionService.saveOrUpdate(permission);
        return "redirect:/permission";
    }

    @PreAuthorize("hasAuthority('sys:permission:delete')")
    @DeleteMapping("/{id}") // /permission/{id}
    @ResponseBody
    public R deleteById(@PathVariable("id") Long id) {
        sysPermissionService.deleteById(id);
        return R.ok();
    }

}