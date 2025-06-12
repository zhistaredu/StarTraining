package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色表 数据层
 *
 * @author edu
 */
@Repository
public interface SysRoleMapper
{
    /**
     * 根据条件分页查询角色数据
     *
     * @param conditions 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(Map<String,Object> conditions);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<SysRole> selectRolePermissionByUserId(String userId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    public List<SysRole> selectRoleAll();

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<String> selectRoleListByUserId(String userId);

    /**
     * 通过角色ID查询角色
     *
     * @param conditions 角色ID
     * @return 角色对象信息
     */
    public SysRole selectRoleById(Map<String,Object> conditions);

    /**
     * 根据用户ID查询角色
     *
     * @param userName 用户名
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserName(String userName);

    /**
     * 校验角色名称是否唯一
     *
     * @param conditions 角色名称
     * @return 角色信息
     */
    public SysRole checkRoleNameUnique(Map<String,Object> conditions);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleKey 角色权限
     * @return 角色信息
     */
    public SysRole checkRoleKeyUnique(String roleKey);

    /**
     * 修改角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(SysRole role);

    /**
     * 新增角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(SysRole role);

    public int insertRoleAll(@Param("insertData") List insertData);

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleById(String roleId);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(String[] roleIds);
}
