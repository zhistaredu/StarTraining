package com.edu.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.system.domain.vo.ViewVisibleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.SysUserPostMapper;
import com.edu.system.domain.SysUserPost;
import com.edu.system.service.ISysUserPostService;

/**
 * 用户与岗位关联Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@Service
public class SysUserPostServiceImpl implements ISysUserPostService 
{
    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    /**
     * 查询用户与岗位关联
     * 
     * @param conditions 用户与岗位关联主键
     * @return 用户与岗位关联
     */
    @Override
    public SysUserPost selectSysUserPostById(Map<String,Object> conditions)
    {
        return sysUserPostMapper.selectSysUserPostById(conditions);
    }

    /**
     * 查询用户与岗位关联列表
     * 
     * @param conditions 用户与岗位关联
     * @return 用户与岗位关联
     */
    @Override
    public List<SysUserPost> selectSysUserPostList(Map<String,Object> conditions)
    {
        return sysUserPostMapper.selectSysUserPostList(conditions);
    }

    @Override
    public int selectSysUserPostCount(Map<String,Object> conditions)
    {
        return sysUserPostMapper.selectSysUserPostCount(conditions);
    }

    /**
     * 新增用户与岗位关联
     * 
     * @param postId 用户与岗位关联
     * @param userIds 用户与岗位关联
     * @return 结果
     */
    @Override
    public int insertSysUserPost(String postId, String userIds)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        List<SysUserPost> list = new ArrayList<SysUserPost>();
        List<ViewVisibleVo> ids = JSON.parseArray(userIds,ViewVisibleVo.class);
        for (ViewVisibleVo items : ids) {
            SysUserPost userPost = new SysUserPost();
            userPost.setUserId(items.getUuid());
            userPost.setUserName(items.getShowName());
            userPost.setPostId(postId);
            userPost.setIsDelete(0);
            userPost.setCompanyId(companyId);
            list.add(userPost);
        }
        if (list.size() > 0){
            sysUserPostMapper.insertSysUserPostAll(list);
        }
        return 1;
    }

    /**
     * 修改用户与岗位关联
     * 
     * @param sysUserPost 用户与岗位关联
     * @return 结果
     */
    @Override
    public int updateSysUserPost(SysUserPost sysUserPost)
    {
        return sysUserPostMapper.updateSysUserPost(sysUserPost);
    }

    /**
     * 批量删除用户与岗位关联
     * 
     * @param ids 需要删除的用户与岗位关联主键
     * @return 结果
     */
    @Override
    public int deleteSysUserPostByIds(Long[] ids)
    {
        return sysUserPostMapper.deleteSysUserPostByIds(ids);
    }

    /**
     * 删除用户与岗位关联信息
     * 
     * @param userPost 用户与岗位关联主键
     * @return 结果
     */
    @Override
    public int deleteSysUserPostById(SysUserPost userPost)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        userPost.setIsDelete(1);
        userPost.setUpdateTime(DateUtils.getTime());
        userPost.setCompanyId(companyId);
        return sysUserPostMapper.updateSysUserPost(userPost);
    }
}
