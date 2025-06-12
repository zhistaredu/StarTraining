package com.edu.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.system.domain.SysUserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.SysPostGroupMapper;
import com.edu.system.domain.SysPostGroup;
import com.edu.system.service.ISysPostGroupService;

/**
 * 岗位组Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@Service
public class SysPostGroupServiceImpl implements ISysPostGroupService 
{
    @Autowired
    private SysPostGroupMapper sysPostGroupMapper;

    /**
     * 查询岗位组
     * 
     * @param conditions 岗位组主键
     * @return 岗位组
     */
    @Override
    public SysPostGroup selectSysPostGroupById(Map<String,Object> conditions)
    {
        return sysPostGroupMapper.selectSysPostGroupById(conditions);
    }

    /**
     * 查询岗位组列表
     * 
     * @param conditions 岗位组
     * @return 岗位组
     */
    @Override
    public List<SysPostGroup> selectSysPostGroupList(Map<String,Object> conditions)
    {
        return sysPostGroupMapper.selectSysPostGroupList(conditions);
    }

    /**
     * 新增岗位组
     * 
     * @param sysPostGroup 岗位组
     * @return 结果
     */
    @Override
    public int insertSysPostGroup(SysPostGroup sysPostGroup)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String time      = DateUtils.getTime();
        sysPostGroup.setUuid(UUID.randomUUID().toString());
        sysPostGroup.setCreateTime(time);
        sysPostGroup.setUpdateTime(time);
        sysPostGroup.setCreateUserId(userId);
        sysPostGroup.setUpdateUserId(userId);
        sysPostGroup.setCompanyId(companyId);
        return sysPostGroupMapper.insertSysPostGroup(sysPostGroup);
    }

    /**
     * 修改岗位组
     * 
     * @param sysPostGroup 岗位组
     * @return 结果
     */
    @Override
    public int updateSysPostGroup(SysPostGroup sysPostGroup)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String time      = DateUtils.getTime();
        sysPostGroup.setUpdateTime(time);
        sysPostGroup.setUpdateUserId(userId);
        sysPostGroup.setCompanyId(companyId);
        return sysPostGroupMapper.updateSysPostGroup(sysPostGroup);
    }

    /**
     * 批量删除岗位组
     * 
     * @param ids 需要删除的岗位组主键
     * @return 结果
     */
    @Override
    public int deleteSysPostGroupByIds(Long[] ids)
    {
        return sysPostGroupMapper.deleteSysPostGroupByIds(ids);
    }

    /**
     * 删除岗位组信息
     * 
     * @param uuid 岗位组主键
     * @return 结果
     */
    @Override
    public int deleteSysPostGroupById(String uuid)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        SysPostGroup postGroup = new SysPostGroup();
        postGroup.setUuid(uuid);
        postGroup.setIsDelete(1);
        postGroup.setUpdateTime(DateUtils.getTime());
        postGroup.setUpdateUserId(userInfo.getUserId());
        postGroup.setCompanyId(companyId);
        return sysPostGroupMapper.updateSysPostGroup(postGroup);
    }
}
