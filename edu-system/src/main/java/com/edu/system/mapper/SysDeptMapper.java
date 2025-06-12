package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.edu.common.core.domain.entity.SysDept;
import org.springframework.stereotype.Repository;

/**
 * 部门管理 数据层
 *
 * @author edu
 */
@Repository
public interface SysDeptMapper
{
    /**
     * 查询部门管理数据
     *
     * @param conditions 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(Map<String,Object> conditions);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptByList(Map<String,Object> dept);

    /**
     * 根据角色ID查询部门树信息
     *
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
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    public List<SysDept> selectChildrenDeptById(String deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(String deptId);

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int hasChildByDeptId(String deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(String deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") String parentId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 新增部门信息
     *
     * @param insertData 部门信息
     * @return 结果
     */
    public int insertDeptAll(@Param("insertData") List insertData);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    public int updateDeptStatus(SysDept dept);

    /**
     * 修改所在部门正常状态
     *
     * @param deptIds 部门ID组
     */
    public void updateDeptStatusNormal(String[] deptIds);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

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
     * @param conditions 部门ID
     * @return 结果
     */
    public List<SysDept> getDeptTree(Map<String,Object> conditions);
}
