package com.edu.web.controller.system;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.edu.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.annotation.Log;
import com.edu.common.constant.UserConstants;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.core.domain.entity.SysRole;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.core.page.TableDataInfo;
import com.edu.common.enums.BusinessType;
import com.edu.common.utils.poi.ExcelUtil;
import com.edu.system.domain.SysUserRole;
import com.edu.system.service.ISysRoleService;
import com.edu.system.service.ISysUserService;

/**
 * 角色信息
 * 
 * @author edu
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController
{
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysUserService userService;

    @GetMapping("/list")
    public AjaxResult list(SysRole role)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("delFlag",0);
        conditions.put("companyId",getLoginUser().getCompanyId());
        List<SysRole> list = roleService.selectRoleList(conditions);
        List<Map<String,Object>> result = new ArrayList<>();
        for (SysRole items : list) {
            Map<String,Object> data = new HashMap<>();
            data.put("roleId",items.getRoleId());
            data.put("roleName",items.getRoleName());
            data.put("roleKey",items.getRoleKey());
            data.put("type","role");
            data.put("uuid",items.getRoleId());
            data.put("show_name",items.getRoleName());
            result.add(data);
        }
        return AjaxResult.success(result);
    }

    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysRole role)
    {
        SysUser user = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("delFlag",0);
        conditions.put("companyId",user.getCompanyId());
        List<SysRole> list = roleService.selectRoleList(conditions);
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        util.exportExcel(response, list, "角色数据");
    }

    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable String roleId)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> where = new HashMap<>();
        where.put("roleId",roleId);
        where.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(roleService.selectRoleById(where));
    }

    /**
     * 新增角色
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysRole role)
    {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role)))
        {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        role.setCompanyId(getLoginUser().getCompanyId());
        role.setCreateBy(getUsername());
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysRole role)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        role.setCompanyId(companyId);
        Map<String,String> conditions = new HashMap<>();
        conditions.put("roleId",role.getRoleId());
        conditions.put("companyId",companyId);
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role)))
        {
            return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        role.setUpdateBy(getUsername());

        roleService.updateRole(role);
        return AjaxResult.success();
    }

    /**
     * 修改保存数据权限
     */
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public AjaxResult dataScope(@RequestBody SysUserRole role)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("roleId",role.getRoleId());
        conditions.put("userId",role.getUserId());
        conditions.put("scopeType",role.getScopeType());
        conditions.put("scopeIds",role.getScopeIds());
        conditions.put("companyId",userInfo.getCompanyId());
        userRoleService.updateUserRole(conditions);
        return toAjax(1);
    }

    /**
     * 删除角色
     */
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable String[] roleIds)
    {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        return AjaxResult.success(roleService.selectRoleAll());
    }

    /**
     * 查询已分配用户角色列表
     */
    @GetMapping("/authUser/allocatedList")
    public TableDataInfo allocatedList(SysUserRole user)
    {
        List<Map<String,Object>> result = new ArrayList<>();
        if (user.getRoleId() == null){
            return getDataTable(result);
        }
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",userInfo.getCompanyId());
        conditions.put("roleId",user.getRoleId());
        conditions.put("isDelete",0);
        Set<String> scopeIds = userInfo.getScopeIds();
        if (!scopeIds.contains("1")){
            conditions.put("scopeIds",scopeIds);
        }
        startPage();
        List<SysUserRole> list = userRoleService.selectUserRoleByList(conditions);
        for (SysUserRole items : list) {
            Map<String,Object> data = new HashMap<>();
            data.put("roleId",items.getRoleId());
            data.put("userId",items.getUserId());
            data.put("userName",items.getUserName());
            data.put("roleType",items.getRoleType());
            data.put("scopeType",items.getScopeType());
            data.put("scopeIds",items.getScopeIds());
            data.put("scopeNames",items.getScopeNames());
            data.put("scopes",items.getScopes());
            data.put("type","user");
            data.put("uuid",items.getUserId());
            data.put("show_name",items.getUserName());
            result.add(data);
        }
        return getDataTable(result);
    }

    /**
     * 查询未分配用户角色列表
     */
    @GetMapping("/authUser/unallocatedList")
    public TableDataInfo unallocatedList(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectUnallocatedList(user);
        return getDataTable(list);
    }

    /**
     * 取消授权用户
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public AjaxResult cancelAuthUser(@RequestBody SysUserRole userRole)
    {
        userRole.setIsDelete(1);
        userRole.setCompanyId(getLoginUser().getCompanyId());
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public AjaxResult cancelAuthUserAll(String roleId, String[] userIds)
    {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public AjaxResult selectAuthUserAll(@RequestBody SysUserRole userRole)
    {
        String roleId = userRole.getRoleId();
        String[] userIds = userRole.getUserIds();
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }
}
