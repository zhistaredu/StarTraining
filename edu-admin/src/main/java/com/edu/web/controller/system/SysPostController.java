package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.system.service.ISysUserPostService;
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
import com.edu.system.domain.SysPost;
import com.edu.system.service.ISysPostService;
import com.edu.common.core.page.TableDataInfo;

/**
 * 岗位信息Controller
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController
{
    @Autowired
    private ISysPostService sysPostService;

    @Autowired
    private ISysUserPostService sysUserPostService;

    /**
     * 查询岗位信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysPost sysPost)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("groupId",sysPost.getGroupId());
        conditions.put("companyId",userInfo.getCompanyId());
        startPage();
        List<SysPost> list = sysPostService.selectSysPostList(conditions);
        return getDataTable(list);
    }

    /**
     * 获取岗位信息详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(sysPostService.selectSysPostById(conditions));
    }

    /**
     * 新增岗位信息
     */
    @Log(title = "岗位信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPost sysPost)
    {
        return toAjax(sysPostService.insertSysPost(sysPost));
    }

    /**
     * 修改岗位信息
     */
    @Log(title = "岗位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPost sysPost)
    {
        return toAjax(sysPostService.updateSysPost(sysPost));
    }

    /**
     * 删除岗位信息
     */
    @Log(title = "岗位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuid}")
    public AjaxResult remove(@PathVariable String uuid)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("postId",uuid);
        conditions.put("isDelete",0);
        conditions.put("companyId",companyId);
        int count = sysUserPostService.selectSysUserPostCount(conditions);
        if (count > 0){
            return AjaxResult.error("请先删除岗位所属的人员");
        }
        return toAjax(sysPostService.deleteSysPostById(uuid));
    }
}
