package com.edu.system.service.impl;

import com.edu.system.domain.SysUserRole;
import com.edu.system.mapper.SysUserRoleMapper;
import com.edu.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService
{
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    public List<SysUserRole> selectUserRoleByList(Map<String,Object> conditions){
        List<SysUserRole> list = userRoleMapper.selectSysUserRoleByList(conditions);
        if (!list.isEmpty()){
            for (SysUserRole items : list){
                //获取数据权限范围--页面显示使用
                Long scopeType = items.getScopeType();
                if (scopeType == 1){
                    items.setScopeNames("全公司");
                } else if (scopeType == 2) {
                    items.setScopeNames("自定义数据权限");
                } else if (scopeType == 3) {
                    items.setScopeNames("所在部门及以下数据权限");
                }
            }
        }
        return list;
    }

    public List<SysUserRole> selectUserRoleData(Map<String,Object> conditions){
        return userRoleMapper.selectSysUserRoleList(conditions);
    }

    public SysUserRole selectUserRoleInfo(Map<String,Object> conditions)
    {
        SysUserRole info = userRoleMapper.selectSysUserRoleById(conditions);
        return info;
    }

    public int updateUserRole(Map<String,Object> conditions){
        return userRoleMapper.updateUserRole(conditions);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param conditions 用户信息
     * @return 用户信息集合信息
     */
    public Map<String,Object> selectUserRoleList(Map<String,Object> conditions)
    {
        Set<String> scopeIds = new HashSet<>();
        scopeIds.add("1");
        Set<String> allDeptParent = new HashSet<>();
        String deptParentPath = "1";
        String[] parentPath = deptParentPath.split(",");
        allDeptParent.addAll(new HashSet<>(Arrays.asList(parentPath)));
        Map<String,Object> outputItems = new HashMap<>();
        outputItems.put("scopeIds",scopeIds);
        outputItems.put("allDeptParent",allDeptParent);
        return outputItems;
    }

    @Override
    public void buildUserRole(String corpId,String authUserId,String agentId)
    {

    }

    /**
     * 自建应用获取管理员列表
     * @param corpId
     * @param authUserId
     */
    @Override
    public void dingSelfBuildUserRole(String corpId,String authUserId)
    {

    }

    /**
     * isv获取管理员列表
     * @param corpId
     * @param authUserId
     */
    @Override
    public void dingBuildUserRole(String corpId,String authUserId)
    {

    }
}
