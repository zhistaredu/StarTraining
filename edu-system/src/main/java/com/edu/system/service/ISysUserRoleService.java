package com.edu.system.service;

import com.edu.common.core.domain.entity.SysUserPath;
import com.edu.system.domain.SysUserRole;

import java.util.List;
import java.util.Map;

public interface ISysUserRoleService {

    public List<SysUserRole> selectUserRoleByList(Map<String,Object> conditions);

    public List<SysUserRole> selectUserRoleData(Map<String,Object> conditions);

    /**
     * 根据条件分页查询用户列表
     *
     * @param conditions 用户信息
     * @return 用户信息集合信息
     */
    public Map<String,Object> selectUserRoleList(Map<String,Object> conditions);
    public SysUserRole selectUserRoleInfo(Map<String,Object> conditions);

    public int updateUserRole(Map<String,Object> conditions);

    public void buildUserRole(String corpId,String authUserId,String agentId);

    public void dingBuildUserRole(String corpId,String authUserId);

    public void dingSelfBuildUserRole(String corpId,String authUserId);

}
