package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.annotation.Log;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.enums.BusinessType;
import com.edu.system.domain.SysPostGroup;
import com.edu.system.service.ISysPostGroupService;
import com.edu.common.core.page.TableDataInfo;

/**
 * 岗位组Controller
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@RestController
@RequestMapping("/system/postGroup")
public class SysPostGroupController extends BaseController
{
    @Autowired
    private ISysPostGroupService sysPostGroupService;

    @Autowired
    private ISysPostService sysPostService;

    /**
     * 查询岗位组列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysPostGroup sysPostGroup)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("isDelete",0);
        conditions.put("companyId",userInfo.getCompanyId());
        startPage();
        List<SysPostGroup> list = sysPostGroupService.selectSysPostGroupList(conditions);
        return getDataTable(list);
    }

    /**
     * 获取岗位组详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(sysPostGroupService.selectSysPostGroupById(conditions));
    }

    /**
     * 新增岗位组
     */
    @Log(title = "岗位组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPostGroup sysPostGroup)
    {
        return toAjax(sysPostGroupService.insertSysPostGroup(sysPostGroup));
    }

    /**
     * 修改岗位组
     */
    @Log(title = "岗位组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPostGroup sysPostGroup)
    {
        return toAjax(sysPostGroupService.updateSysPostGroup(sysPostGroup));
    }

    /**
     * 删除岗位组
     */
    @Log(title = "岗位组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuid}")
    public AjaxResult remove(@PathVariable String uuid)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        //检查当前岗位组下是否有岗位
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("groupId",uuid);
        conditions.put("isDelete",0);
        conditions.put("companyId",companyId);
        int count = sysPostService.selectSysPostCount(conditions);
        if (count > 0){
            return AjaxResult.error("请先删除岗位组所属的岗位");
        }
        return toAjax(sysPostGroupService.deleteSysPostGroupById(uuid));
    }
}
