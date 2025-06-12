package com.edu.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.edu.common.core.domain.entity.SysDept;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.system.domain.vo.DeptVisibleVo;
import com.edu.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.common.constant.UserConstants;
import com.edu.common.core.domain.TreeSelect;
import com.edu.common.core.text.Convert;
import com.edu.common.exception.ServiceException;
import com.edu.common.utils.SecurityUtils;
import com.edu.common.utils.StringUtils;
import com.edu.common.utils.spring.SpringUtils;
import com.edu.system.mapper.SysDeptMapper;

/**
 * 部门管理 服务实现
 * 
 * @author edu
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询部门管理数据
     * 
     * @param conditions 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysDept> selectDeptList(Map<String,Object> conditions)
    {
        return deptMapper.selectDeptList(conditions);
    }

    /**
     * 查询部门数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysDept> selectDeptByList(Map<String,Object> dept)
    {
        return deptMapper.selectDeptByList(dept);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<String> tempList = new ArrayList<String>();
        for (SysDept dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();)
        {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param conditions 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<String> selectDeptListByRoleId(Map<String,Object> conditions)
    {
        return deptMapper.selectDeptListByRoleId(conditions);
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param conditions 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Map<String,Object> conditions)
    {
        return deptMapper.selectDeptById(conditions);
    }

    /**
     * 根据部门ID获取部门下面所有的部门列表
     *
     * @param conditions 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptByIdList(Map<String,Object> conditions)
    {
        Map<String,Object> dept = new HashMap<>();
        dept.put("company_id",conditions.get("companyId"));
        List<SysDept> deptList = selectDeptList(dept);
        Map<String,Object> allDept = new HashMap<>();
        for (SysDept deptInfo : deptList){
            allDept.put(deptInfo.getDeptId(),deptInfo);
        }
        return deptMapper.selectDeptByIdList(conditions);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     * 
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(String deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(String deptId)
    {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0;
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(String deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        String deptId = StringUtils.isNull(dept.getDeptId()) ? "" : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && !info.getDeptId().equals(deptId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验部门是否有数据权限
     * 
     * @param deptId 部门id
     */
    @Override
    public void checkDeptDataScope(String deptId)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            Map<String,Object> dept = new HashMap<>();
            dept.put("deptId",deptId);
            dept.put("companyId",companyId);
            List<SysDept> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
            if (StringUtils.isEmpty(depts))
            {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        String deptId    = UUID.randomUUID().toString();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",companyId);
        conditions.put("deptId",dept.getParentId());
        SysDept info = deptMapper.selectDeptById(conditions);
        // 如果父节点不为正常状态,则不允许新增子节点
        if (info.getStatus() != 1)
        {
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setStatus(1);
        dept.setDeptIdPath(info.getDeptIdPath() + "," + dept.getParentId());
        dept.setDeptId(deptId);
        dept.setDeptIdPath(info.getDeptIdPath()+","+deptId);
        dept.setDeptNamePath(info.getDeptNamePath()+","+dept.getDeptName());
        return deptMapper.insertDept(dept);
    }

    @Override
    public int updateDeptInfo(SysDept dept){
        return deptMapper.updateDept(dept);
    }

    @Override
    public int updateDeptStatus(SysDept dept){
        return deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("deptId",dept.getParentId());
        conditions.put("companyId",companyId);
        SysDept parentDept = deptMapper.selectDeptById(conditions);
        conditions.put("deptId",dept.getDeptId());
        SysDept oldDept = deptMapper.selectDeptById(conditions);
        if (StringUtils.isNotNull(parentDept) && StringUtils.isNotNull(oldDept))
        {
            String newDeptIdPath = parentDept.getDeptIdPath() + "," + dept.getDeptId();
            String oldDeptIdPath = oldDept.getDeptIdPath();
            String newDeptNamePath = parentDept.getDeptNamePath() + "," + dept.getDeptName();
            String oldDeptNamePath = oldDept.getDeptNamePath();
            dept.setDeptIdPath(newDeptIdPath);
            dept.setDeptNamePath(newDeptNamePath);
            updateDeptChildren(dept.getDeptId(), newDeptIdPath, oldDeptIdPath,newDeptNamePath,oldDeptNamePath);
        }
        return deptMapper.updateDept(dept);
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysDept dept)
    {
        String ancestors = dept.getDeptIdPath();
        String[] deptIds = Convert.toStrArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 被修改的部门ID
     * @param newDeptIdPath 新的父ID集合
     * @param oldDeptIdPath 旧的父ID集合
     */
    public void updateDeptChildren(String deptId, String newDeptIdPath, String oldDeptIdPath,String newDeptNamePath,String oldDeptNamePath)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setDeptIdPath(child.getDeptIdPath().replaceFirst(oldDeptIdPath, newDeptIdPath));
            child.setDeptNamePath(child.getDeptNamePath().replaceFirst(oldDeptNamePath,newDeptNamePath));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(String deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().equals(t.getDeptId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 查询部门并检查是否有下级部门
     *
     * @param conditions 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> getDeptTree(Map<String,Object> conditions){
        return deptMapper.getDeptTree(conditions);
    }

    @Override
    public List<SysDept> getVisibleDeptList(Map<String,Object> conditions){
        Set<String> scopeIds = SecurityUtils.getLoginUser().getScopeIds();
        List<SysDept> list = selectDeptList(conditions);
        if (list != null){
            Iterator<SysDept> iterator = list.iterator();
            while (iterator.hasNext()) {
                SysDept deptPath = iterator.next();
                String parentPath = deptPath.getDeptIdPath();
                List<String> path = Arrays.asList(parentPath.split(","));
                for (String scope : scopeIds){
                    if (!path.contains(scope)){
                        iterator.remove();
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<DeptVisibleVo> getVisibleDeptAloneList(Map<String,Object> conditions){
        List<SysDept> list = selectDeptByList(conditions);
        List<DeptVisibleVo> data = new ArrayList<>();
        if(!list.isEmpty()){
            for (SysDept dept : list){
                DeptVisibleVo deptVisibleVo = new DeptVisibleVo();
                deptVisibleVo.setUuid(dept.getDeptId());
                deptVisibleVo.setShowName(dept.getDeptName());
                deptVisibleVo.setParentId(dept.getParentId());
                deptVisibleVo.setType("dept");
                data.add(deptVisibleVo);
            }
        }
        return data;
    }

    @Override
    public List<SysDept> getLazyTreeselect(Map<String,Object> conditions){
        Set<String> scopeIds = SecurityUtils.getLoginUser().getScopeIds();
        List<SysDept> list = deptMapper.selectDeptList(conditions);
        if (list != null){
            Iterator<SysDept> iterator = list.iterator();
            while (iterator.hasNext()) {
                SysDept deptPath = iterator.next();
                String parentPath = deptPath.getDeptIdPath();
                List<String> path = Arrays.asList(parentPath.split(","));
                for (String scope : scopeIds){
                    if (!path.contains(scope)){
                        iterator.remove();
                    }
                }
            }
        }
        return list;
    }
}
