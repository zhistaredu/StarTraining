package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.TreeSelect;
import com.edu.common.core.domain.entity.SysDept;
import com.edu.system.domain.vo.DeptVisibleVo;

/**
 * 部门管理 服务层
 * 
 * @author edu
 */
public interface ISysDeptService
{
    /**
     * 查询部门管理数据
     * 
     * @param condi 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(Map<String,Object> condi);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptByList(Map<String,Object> dept);

    /**
     * 构建前端所需要树结构
     * 
     * @param depts 部门列表
     * @return 树结构列表
     */
    public List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 根据角色ID查询部门树信息
     * 
     * @param conditions 角色ID
     * @return 选中部门列表
     */
    public List<String> selectDeptListByRoleId(Map<String,Object> conditions);

    /**
     * 根据部门ID查询信息
     * 
     * @param conditions 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Map<String,Object> conditions);

    /**
     * 根据部门ID获取部门下面所有的部门列表
     *
     * @param conditions 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptByIdList(Map<String,Object> conditions);

    /**
     * 根据ID查询所有子部门（正常状态）
     * 
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(String deptId);

    /**
     * 是否存在部门子节点
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean hasChildByDeptId(String deptId);

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(String deptId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDept dept);

    /**
     * 校验部门是否有数据权限
     * 
     * @param deptId 部门id
     */
    public void checkDeptDataScope(String deptId);

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    public int updateDeptStatus(SysDept dept);

    public int updateDeptInfo(SysDept dept);

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(String deptId);


    /**
     * 查询部门并检查是否有下级部门
     *
     * @param conditions 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> getDeptTree(Map<String,Object> conditions);


    public List<SysDept> getVisibleDeptList(Map<String,Object> conditions);

    public List<DeptVisibleVo> getVisibleDeptAloneList(Map<String,Object> conditions);

    public List<SysDept> getLazyTreeselect(Map<String,Object> conditions);

}
