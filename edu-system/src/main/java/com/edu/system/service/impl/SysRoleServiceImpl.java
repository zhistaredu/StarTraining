package com.edu.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.edu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.common.constant.UserConstants;
import com.edu.common.core.domain.entity.SysRole;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.exception.ServiceException;
import com.edu.common.utils.SecurityUtils;
import com.edu.common.utils.StringUtils;
import com.edu.common.utils.spring.SpringUtils;
import com.edu.system.domain.SysRoleDept;
import com.edu.system.domain.SysRoleMenu;
import com.edu.system.domain.SysUserRole;
import com.edu.system.mapper.SysRoleDeptMapper;
import com.edu.system.mapper.SysRoleMapper;
import com.edu.system.mapper.SysRoleMenuMapper;
import com.edu.system.mapper.SysUserRoleMapper;
import com.edu.system.service.ISysRoleService;

/**
 * 角色 业务层处理
 * 
 * @author edu
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService
{
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    /**
     * 根据条件分页查询角色数据
     * 
     * @param conditions 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> selectRoleList(Map<String,Object> conditions)
    {
        return roleMapper.selectRoleList(conditions);
    }

    /**
     * 根据用户ID查询角色
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(String userId)
    {
        List<SysRole> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().equals(userRole.getRoleId()))
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(String userId)
    {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     * 
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        SysUser user = SecurityUtils.getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",user.getCompanyId());
        return selectRoleList(conditions);
    }

    /**
     * 根据用户ID获取角色选择框列表
     * 
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<String> selectRoleListByUserId(String userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     * 
     * @param conditions 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Map<String,Object> conditions)
    {
        String roleId = (String) conditions.get("roleId");
        String companyId = (String) conditions.get("companyId");
        SysRole info = roleMapper.selectRoleById(conditions);
        if (info != null){
            Map<String,Object> where = new HashMap<>();
            where.put("roleId",roleId);
            where.put("companyId",companyId);
            List<SysRoleMenu> menuList = roleMenuMapper.selectRoleMenuList(where);
            List<String> menuIds = menuList.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toList());
            info.setMenuIds(menuIds);
        }
        return info;
    }

    /**
     * 校验角色名称是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("roleName",role.getRoleName());
        conditions.put("companyId",userInfo.getCompanyId());
        SysRole info = roleMapper.checkRoleNameUnique(conditions);
        if (StringUtils.isNotNull(info) && !role.getRoleId().equals(info.getRoleId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role)
    {
        String roleId = StringUtils.isNull(role.getRoleId()) ? "" : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && !info.getRoleId().equals(roleId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     * 
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("roleId",role.getRoleId());
        conditions.put("companyId",SecurityUtils.getCompanyId());
        SysRole roleInfo = roleMapper.selectRoleById(conditions);
        if (roleInfo.getRoleKey().equals("admin"))
        {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    /**
     * 校验角色是否有数据权限
     * 
     * @param conditions 角色id
     */
    @Override
    public void checkRoleDataScope(Map<String,Object> conditions)
    {
        List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(conditions);
        if (StringUtils.isEmpty(roles))
        {
            throw new ServiceException("没有权限访问角色数据！");
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(String roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role)
    {
        role.setRoleId(UUID.randomUUID().toString());
        role.setRoleKey("normal");
        // 新增角色信息
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role)
    {
        String companyId = role.getCompanyId();
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与菜单关联
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("roleId",role.getRoleId());
        conditions.put("companyId",companyId);
        roleMenuMapper.deleteRoleMenuByRoleId(conditions);
        return insertRoleMenu(role);
    }

    /**
     * 修改角色状态
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(SysRole role)
    {
        return roleMapper.updateRole(role);
    }

    /**
     * 修改数据权限信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role)
    {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜单信息
     * 
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role)
    {
        String companyId = role.getCompanyId();
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (String menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            rm.setCompanyId(companyId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(SysRole role)
    {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (String deptId : role.getDeptIds())
        {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleById(String roleId)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("roleId",roleId);
        conditions.put("companyId",SecurityUtils.getCompanyId());
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(conditions);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     * 
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleByIds(String[] roleIds)
    {
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 取消授权用户角色
     * 
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole)
    {
        return userRoleMapper.updateUserRole(userRole);
    }

    /**
     * 批量取消授权用户角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(String roleId, String[] userIds)
    {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    /**
     * 批量选择授权用户角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(String roleId, String[] userIds)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",companyId);
        conditions.put("roleId",roleId);
        SysRole roleInfo = roleMapper.selectRoleById(conditions);
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (String userId : userIds)
        {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            ur.setRoleType(roleInfo.getRoleKey());
            ur.setScopeType(3L);
            ur.setIsDelete(0);
            ur.setCompanyId(companyId);
            list.add(ur);
        }
        return userRoleMapper.insertUserRoleAll(list);
    }

    @Override
    public void initializeRole(String corpId)
    {
        List<String> roleOne = new ArrayList<>();
        Collections.addAll(roleOne,"主管理员","部门负责人","部门组长");

        String time = DateUtils.getTime();
        List<SysRole> data = new ArrayList<>();
        for (String title : roleOne){
            String roleKey = "";
            if (title.equals("主管理员")){
                roleKey = "admin";
            } else if(title.equals("部门负责人")) {
                roleKey = "dept";
            } else if (title.equals("部门组长")){
                roleKey = "group";
            }
            Integer i = 0;
            i++;
            SysRole role = new SysRole();
            role.setRoleId(UUID.randomUUID().toString());
            role.setRoleName(title);
            role.setRoleKey(roleKey);
            role.setRoleSort(i);
            role.setCreateTime(time);
            role.setUpdateTime(time);
            role.setCompanyId(corpId);
            role.setStatus(0);
            role.setIsDelete(0);
            role.setCompanyId(corpId);
            data.add(role);
        }
        roleMapper.insertRoleAll(data);
    }
}
