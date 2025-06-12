package com.edu.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.SysPostMapper;
import com.edu.system.domain.SysPost;
import com.edu.system.service.ISysPostService;

/**
 * 岗位信息Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@Service
public class SysPostServiceImpl implements ISysPostService 
{
    @Autowired
    private SysPostMapper sysPostMapper;

    /**
     * 查询岗位信息
     * 
     * @param conditions 岗位信息主键
     * @return 岗位信息
     */
    @Override
    public SysPost selectSysPostById(Map<String,Object> conditions)
    {
        return sysPostMapper.selectSysPostById(conditions);
    }

    /**
     * 查询岗位信息列表
     * 
     * @param conditions 岗位信息
     * @return 岗位信息
     */
    @Override
    public List<SysPost> selectSysPostList(Map<String,Object> conditions)
    {
        return sysPostMapper.selectSysPostList(conditions);
    }

    @Override
    public int selectSysPostCount(Map<String,Object> conditions)
    {
        return sysPostMapper.selectSysPostCount(conditions);
    }

    /**
     * 新增岗位信息
     * 
     * @param sysPost 岗位信息
     * @return 结果
     */
    @Override
    public int insertSysPost(SysPost sysPost)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String time      = DateUtils.getTime();
        sysPost.setPostId(UUID.randomUUID().toString());
        sysPost.setCreateTime(time);
        sysPost.setUpdateTime(time);
        sysPost.setCreateUserId(userId);
        sysPost.setUpdateUserId(userId);
        sysPost.setCompanyId(companyId);
        return sysPostMapper.insertSysPost(sysPost);
    }

    /**
     * 修改岗位信息
     * 
     * @param sysPost 岗位信息
     * @return 结果
     */
    @Override
    public int updateSysPost(SysPost sysPost)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String time      = DateUtils.getTime();
        sysPost.setUpdateTime(time);
        sysPost.setUpdateUserId(userId);
        sysPost.setCompanyId(companyId);
        return sysPostMapper.updateSysPost(sysPost);
    }

    /**
     * 批量删除岗位信息
     * 
     * @param ids 需要删除的岗位信息主键
     * @return 结果
     */
    @Override
    public int deleteSysPostByIds(Long[] ids)
    {
        return sysPostMapper.deleteSysPostByIds(ids);
    }

    /**
     * 删除岗位信息信息
     * 
     * @param uuid 岗位信息主键
     * @return 结果
     */
    @Override
    public int deleteSysPostById(String uuid)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String time      = DateUtils.getTime();
        SysPost postInfo = new SysPost();
        postInfo.setPostId(uuid);
        postInfo.setIsDelete(0);
        postInfo.setUpdateTime(time);
        postInfo.setUpdateUserId(userId);
        postInfo.setCompanyId(companyId);
        return sysPostMapper.updateSysPost(postInfo);
    }
}
