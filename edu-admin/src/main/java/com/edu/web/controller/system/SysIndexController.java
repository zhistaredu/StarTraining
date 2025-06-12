package com.edu.web.controller.system;

import com.edu.common.core.domain.AjaxResult;
import com.edu.common.core.domain.entity.SysMenu;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.SecurityUtils;
import com.edu.framework.web.service.SysPermissionService;
import com.edu.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.config.EduConfig;
import com.edu.common.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 首页
 *
 * @author edu
 */
@RestController
public class SysIndexController
{
    /** 系统基础配置 */
    @Autowired
    private EduConfig eduConfig;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("欢迎使用云上CT企业培训系统","", "");
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser();
        // 角色集合和权限集合
        Map<String, Set<String>> rolesAndPerms = permissionService.getRolePermission(user);
        //角色和菜单都不为空则是后台管理员
        Map<String,Object> master = new HashMap<>();
        master.put("is_master",0);
        if (!rolesAndPerms.get("roles").isEmpty() || !rolesAndPerms.get("perms").isEmpty()){
            master.put("is_master",1);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", rolesAndPerms.get("roles"));
        ajax.put("permissions", rolesAndPerms.get("perms"));
        ajax.put("status","ok");
        ajax.put("data",master);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        String userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
