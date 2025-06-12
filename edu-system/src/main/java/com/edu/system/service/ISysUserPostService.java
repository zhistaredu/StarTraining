package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.SysUserPost;

/**
 * 用户与岗位关联Service接口
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
public interface ISysUserPostService 
{
    /**
     * 查询用户与岗位关联
     * 
     * @param conditions 用户与岗位关联主键
     * @return 用户与岗位关联
     */
    public SysUserPost selectSysUserPostById(Map<String,Object> conditions);

    /**
     * 查询用户与岗位关联列表
     * 
     * @param conditions 用户与岗位关联
     * @return 用户与岗位关联集合
     */
    public List<SysUserPost> selectSysUserPostList(Map<String,Object> conditions);

    public int selectSysUserPostCount(Map<String,Object> conditions);

    /**
     * 新增用户与岗位关联
     * 
     * @param roleId 用户与岗位关联
     * @param userIds 用户与岗位关联
     * @return 结果
     */
    public int insertSysUserPost(String roleId, String userIds);

    /**
     * 修改用户与岗位关联
     * 
     * @param sysUserPost 用户与岗位关联
     * @return 结果
     */
    public int updateSysUserPost(SysUserPost sysUserPost);

    /**
     * 批量删除用户与岗位关联
     * 
     * @param ids 需要删除的用户与岗位关联主键集合
     * @return 结果
     */
    public int deleteSysUserPostByIds(Long[] ids);

    /**
     * 删除用户与岗位关联信息
     * 
     * @param userPost 用户与岗位关联主键
     * @return 结果
     */
    public int deleteSysUserPostById(SysUserPost userPost);
}
