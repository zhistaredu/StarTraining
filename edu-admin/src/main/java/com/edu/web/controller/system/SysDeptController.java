package com.edu.web.controller.system;

import java.util.*;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.system.domain.SysUserRole;
import com.edu.system.domain.vo.DeptUserVisibleVo;
import com.edu.system.domain.vo.DeptVisibleVo;
import com.edu.system.service.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.edu.common.annotation.Log;
import com.edu.common.constant.UserConstants;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.core.domain.entity.SysDept;
import com.edu.common.enums.BusinessType;
import com.edu.common.utils.StringUtils;

/**
 * 部门信息
 *
 * @author edu
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysUserService userService;

    /**
     * 获取部门列表
     */
    @GetMapping("/list")
    public AjaxResult list(SysDept dept)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("parentId",dept.getParentId());
        conditions.put("companyId",userInfo.getCompanyId());
        List<SysDept> list = deptService.selectDeptList(conditions);
        return AjaxResult.success(list);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) String deptId)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",companyId);
        List<SysDept> list = deptService.selectDeptList(conditions);
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getDeptIdPath(), ","), deptId + ""))
            {
                it.remove();
            }
        }
        return AjaxResult.success(list);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable String deptId)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        deptService.checkDeptDataScope(deptId);
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("deptId",deptId);
        conditions.put("companyId",companyId);
        return AjaxResult.success(deptService.selectDeptById(conditions));
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect()
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("isDelete",0);
        conditions.put("companyId",userInfo.getCompanyId());
        List<SysDept> deptList = deptService.getVisibleDeptList(conditions);
        return AjaxResult.success(deptService.buildDeptTreeSelect(deptList));
    }

    /**
     * 指派框列表
     */
    @GetMapping("/aloneSelect")
    public AjaxResult aloneSelect(SysDept dept)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        String keyword   = dept.getKeyword();
        Map<String,Object> conditions = new HashMap<>();
        if (StringUtils.isNotEmpty(keyword)){

        }
        String parentId = dept.getParentId();
        if (StringUtils.isEmpty(parentId)){
            conditions.put("scopeIds",userInfo.getScopeIds());
        } else {
            conditions.put("parentId",parentId);
        }
        conditions.put("isDelete",0);
        conditions.put("companyId",companyId);
        List<DeptVisibleVo> deptList = deptService.getVisibleDeptAloneList(conditions);
        return AjaxResult.success(deptList);
    }

    /**
     * 指派框搜索
     */
    @GetMapping("/getContactSearch")
    public AjaxResult getContactSearch(SysDept dept) {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        String keyword   = dept.getKeyword();
        Set<String> scopeIds = userInfo.getScopeIds();
        List<DeptUserVisibleVo> viewData = new ArrayList<>();
        Map<String,DeptUserVisibleVo> viewMap = new HashMap<>();
        if (StringUtils.isNotEmpty(keyword)){
            if (StringUtils.isNotEmpty(keyword)){
                Map<String,Object> conditions = new HashMap<>();
                conditions.put("isDelete",0);
                conditions.put("deptName",keyword);
                conditions.put("companyId",companyId);
                List<SysDept> deptList = deptService.selectDeptList(conditions);
                if (!deptList.isEmpty()){
                    for (SysDept items : deptList){
                        List<String> ids = Arrays.asList(items.getDeptIdPath().split(","));
                        DeptUserVisibleVo deptUserVisibleVo = new DeptUserVisibleVo();
                        if (scopeIds.contains("1")) {
                            deptUserVisibleVo.setUuid(items.getDeptId());
                            deptUserVisibleVo.setShowName(items.getDeptName());
                            deptUserVisibleVo.setType("dept");
                        } else {
                            for (String scope : scopeIds){
                                if (ids.contains(scope)){
                                    deptUserVisibleVo.setUuid(items.getDeptId());
                                    deptUserVisibleVo.setShowName(items.getDeptName());
                                    deptUserVisibleVo.setType("dept");
                                }
                            }
                        }
                        viewMap.put(items.getDeptId(),deptUserVisibleVo);
                    }
                }
                Map<String,Object> where = new HashMap<>();
                where.put("isDelete",0);
                where.put("userName",keyword);
                where.put("companyId",companyId);
                List<SysUser> userList = userService.selectUserList(where);
                if (!userList.isEmpty()){
                    for (SysUser items : userList) {
                        List<String> ids = Arrays.asList(items.getDeptIds().split(","));
                        DeptUserVisibleVo deptUserVisibleVo = new DeptUserVisibleVo();
                        if (scopeIds.contains("1")){
                            deptUserVisibleVo.setUuid(items.getUserId());
                            deptUserVisibleVo.setShowName(items.getUserName());
                            deptUserVisibleVo.setType("user");
                        } else {
                            for (String scope : scopeIds){
                                if (ids.contains(scope)){
                                    deptUserVisibleVo.setUuid(items.getUserId());
                                    deptUserVisibleVo.setShowName(items.getUserName());
                                    deptUserVisibleVo.setType("user");
                                }
                            }
                        }
                        viewMap.put(items.getUserId(),deptUserVisibleVo);
                    }
                }
            }
        }
        if (viewMap.size() > 0){
            viewData = new ArrayList<>(viewMap.values());
        }
        return AjaxResult.success(viewData);
    }

    /**
     * 懒加载获取树型数据
     */
    @GetMapping("/lazyTreeselect")
    public AjaxResult lazyTreeselect(SysDept dept)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("isDelete",0);
        conditions.put("companyId",userInfo.getCompanyId());
        List<SysDept> depts = deptService.getLazyTreeselect(conditions);
        return AjaxResult.success(depts);
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect")
    public AjaxResult roleDeptTreeselect(@RequestParam("roleId") String roleId,@RequestParam("userId") String userId)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        Set<String> scopeIds = userInfo.getScopeIds();
        if (!scopeIds.contains("1")){
            conditions.put("scopeIds",userInfo.getScopeIds());
        }
        conditions.put("isDelete",0);
        conditions.put("companyId",userInfo.getCompanyId());
        List<SysDept> depts = deptService.getVisibleDeptList(conditions);
        conditions.put("roleId",roleId);
        conditions.put("userId",userId);
        SysUserRole userRoles = userRoleService.selectUserRoleInfo(conditions);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", userRoles.getScopeIds().split(","));
        ajax.put("scopeType",userRoles.getScopeType());
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return ajax;
    }

    /**
     * 新增部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(getUsername());
        dept.setCompanyId(companyId);
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept)
    {
        String deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getParentId().equals(deptId))
        {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus().toString()) && deptService.selectNormalChildrenDeptById(deptId) > 0)
        {
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable String deptId)
    {
        if (deptService.hasChildByDeptId(deptId))
        {
            return AjaxResult.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return AjaxResult.error("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }
}
