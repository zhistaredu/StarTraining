package com.edu.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysCompanyUser;
import com.edu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.SysCompanyUserMapper;
import com.edu.system.service.ISysCompanyUserService;

/**
 * 公司管理员Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
@Service
public class SysCompanyUserServiceImpl implements ISysCompanyUserService 
{
    @Autowired
    private SysCompanyUserMapper sysCompanyUserMapper;


    /**
     * 查询公司管理员
     * 
     * @param conditions 公司管理员主键
     * @return 公司管理员
     */
    @Override
    public SysCompanyUser selectSysCompanyUserById(Map<String,Object> conditions)
    {
        return sysCompanyUserMapper.selectSysCompanyUserById(conditions);
    }

    /**
     * 查询公司管理员列表
     * 
     * @param sysCompanyUser 公司管理员
     * @return 公司管理员
     */
    @Override
    public List<SysCompanyUser> selectSysCompanyUserList(SysCompanyUser sysCompanyUser)
    {
        return sysCompanyUserMapper.selectSysCompanyUserList(sysCompanyUser);
    }

    /**
     * 新增公司管理员
     * 
     * @param sysCompanyUser 公司管理员
     * @return 结果
     */
    @Override
    public int insertSysCompanyUser(SysCompanyUser sysCompanyUser)
    {
        sysCompanyUser.setCreateTime(DateUtils.getTime());
        return sysCompanyUserMapper.insertSysCompanyUser(sysCompanyUser);
    }

    /**
     * 修改公司管理员
     * 
     * @param sysCompanyUser 公司管理员
     * @return 结果
     */
    @Override
    public int updateSysCompanyUser(SysCompanyUser sysCompanyUser)
    {
        sysCompanyUser.setUpdateTime(DateUtils.getTime());
        return sysCompanyUserMapper.updateSysCompanyUser(sysCompanyUser);
    }

    /**
     * 批量删除公司管理员
     * 
     * @param ids 需要删除的公司管理员主键
     * @return 结果
     */
    @Override
    public int deleteSysCompanyUserByIds(Long[] ids)
    {
        return sysCompanyUserMapper.deleteSysCompanyUserByIds(ids);
    }

    /**
     * 删除公司管理员信息
     * 
     * @param id 公司管理员主键
     * @return 结果
     */
    @Override
    public int deleteSysCompanyUserById(Long id)
    {
        return sysCompanyUserMapper.deleteSysCompanyUserById(id);
    }

    @Override
    public void buildCompanyUser(String corpId,String authUserId,String agentId)
    {
        List<Map<String,Object>> insertList = new ArrayList();

        if (!insertList.isEmpty()){
            sysCompanyUserMapper.insertSysCompanyUserAll(insertList);
        }
    }
}
