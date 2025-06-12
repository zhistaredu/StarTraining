package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edu.common.annotation.Log;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.enums.BusinessType;
import com.edu.system.domain.SysUserPost;
import com.edu.system.service.ISysUserPostService;
import com.edu.common.core.page.TableDataInfo;

/**
 * 用户与岗位关联Controller
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@RestController
@RequestMapping("/system/userPost")
public class SysUserPostController extends BaseController
{
    @Autowired
    private ISysUserPostService sysUserPostService;

    /**
     * 查询用户与岗位关联列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysUserPost userPost)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("postId",userPost.getPostId());
        conditions.put("isDelete",0);
        conditions.put("companyId",userInfo.getCompanyId());
        startPage();
        List<SysUserPost> list = sysUserPostService.selectSysUserPostList(conditions);
        return getDataTable(list);
    }

    /**
     * 获取用户与岗位关联详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(sysUserPostService.selectSysUserPostById(conditions));
    }

    /**
     * 新增用户与岗位关联
     */
    @Log(title = "用户与岗位关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserPost sysUserPost)
    {
        String postId  = sysUserPost.getPostId();
        String userIds = sysUserPost.getData();
        return toAjax(sysUserPostService.insertSysUserPost(postId,userIds));
    }

    /**
     * 修改用户与岗位关联
     */
    @Log(title = "用户与岗位关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserPost sysUserPost)
    {
        return toAjax(sysUserPostService.updateSysUserPost(sysUserPost));
    }

    /**
     * 删除用户与岗位关联
     */
    @Log(title = "用户与岗位关联", businessType = BusinessType.DELETE)
	@DeleteMapping()
    public AjaxResult remove(SysUserPost userPost)
    {
        return toAjax(sysUserPostService.deleteSysUserPostById(userPost));
    }
}
