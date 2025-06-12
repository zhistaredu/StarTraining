package com.edu.framework.web.service;

import java.util.*;
import java.util.stream.Collectors;

import com.edu.common.utils.SecurityUtils;
import com.edu.system.domain.SysUserRole;
import com.edu.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.system.service.ISysMenuService;

/**
 * 用户权限处理
 * 
 * @author edu
 */
@Component
public class SysPermissionService
{
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysUserRoleService userRoleService;

    /**
     * 获取角色数据权限
     * 
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Map<String,Set<String>> getRolePermission(SysUser user)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId", SecurityUtils.getCompanyId());
        conditions.put("isDelete",0);
        conditions.put("userId",user.getUserId());
        List<SysUserRole> list = userRoleService.selectUserRoleByList(conditions);
        Set<String> roles = list.stream()
                .map(SysUserRole::getRoleType)
                .collect(Collectors.toSet());

        Map<String,Set<String>> result = new HashMap<>();
        result.put("roles",roles);
        // 管理员拥有所有权限
        Set<String> perms = new HashSet<String>();
        if (roles.contains("admin")) {
            perms.add("*:*:*");
        }else{
            perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        result.put("perms",perms);
        return result;
    }

    /**
     * 获取菜单数据权限
     * 
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user)
    {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            perms.add("*:*:*");
        }
        else
        {
            perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }
}
