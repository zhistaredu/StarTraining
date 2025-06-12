package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.edu.system.domain.SysUserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户与角色关联表 数据层
 * 
 * @author edu
 */
@Repository
public interface SysUserRoleMapper
{
    /**
     * 用户与角色表
     *
     * @param conditions 用户路径
     * @return 用户路径集合
     */
    public List<SysUserRole> selectSysUserRoleList(Map<String,Object> conditions);

    public List<SysUserRole> selectSysUserRoleByList(Map<String,Object> conditions);

    public SysUserRole selectSysUserRoleById(Map<String,Object> conditions);

    public int updateUserRole(Map<String,Object> conditions);

    public int insertUserRoleAll(@Param("insertData") List insertData);

    /**
     * 通过用户ID删除用户和角色关联
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserRoleByUserId(String userId);

    /**
     * 批量删除用户和角色关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRole(String[] ids);

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(String roleId);

    /**
     * 批量新增用户角色信息
     * 
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(List<SysUserRole> userRoleList);

    public int updateUserRole(SysUserRole userRole);

    /**
     * 删除用户和角色关联信息
     * 
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    public int deleteUserRoleInfo(SysUserRole userRole);

    /**
     * 批量取消授权用户角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int deleteUserRoleInfos(@Param("roleId") String roleId, @Param("userIds") String[] userIds);
}
