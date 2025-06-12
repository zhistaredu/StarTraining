package com.edu.system.service.impl;

import java.util.*;

import com.edu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.SysUserPathMapper;
import com.edu.common.core.domain.entity.SysUserPath;
import com.edu.system.service.ISysUserPathService;

/**
 * 用户路径Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
@Service
public class SysUserPathServiceImpl implements ISysUserPathService 
{
    @Autowired
    private SysUserPathMapper sysUserPathMapper;

    /**
     * 查询用户人数
     *
     * @param conditions 用户路径主键
     * @return 用户路径
     */
    @Override
    public Integer selectUserPathCount(Map<String,Object> conditions)
    {
        return sysUserPathMapper.selectUserPathCount(conditions);
    }

    /**
     * 查询用户路径
     * 
     * @param conditions 用户路径主键
     * @return 用户路径
     */
    @Override
    public SysUserPath selectSysUserPathById(Map<String,Object> conditions)
    {
        return sysUserPathMapper.selectSysUserPathById(conditions);
    }

    /**
     * 查询用户路径列表
     * 
     * @param conditions 用户路径
     * @return 用户路径
     */
    @Override
    public List<SysUserPath> selectSysUserPathList(Map<String,Object> conditions)
    {

        return sysUserPathMapper.selectSysUserPathList(conditions);
    }

    /**
     * 新增用户路径
     * 
     * @param sysUserPath 用户路径
     * @return 结果
     */
    @Override
    public int insertSysUserPath(SysUserPath sysUserPath)
    {
        sysUserPath.setCreateTime(DateUtils.getTime());
        return sysUserPathMapper.insertSysUserPath(sysUserPath);
    }

    /**
     * 修改用户路径
     * 
     * @param sysUserPath 用户路径
     * @return 结果
     */
    @Override
    public int updateSysUserPath(SysUserPath sysUserPath)
    {
        sysUserPath.setUpdateTime(DateUtils.getTime());
        return sysUserPathMapper.updateSysUserPath(sysUserPath);
    }

    /**
     * 批量删除用户路径
     * 
     * @param ids 需要删除的用户路径主键
     * @return 结果
     */
    @Override
    public int deleteSysUserPathByIds(Long[] ids)
    {
        return sysUserPathMapper.deleteSysUserPathByIds(ids);
    }

    /**
     * 删除用户路径信息
     * 
     * @param id 用户路径主键
     * @return 结果
     */
    @Override
    public int deleteSysUserPathById(Long id)
    {
        return sysUserPathMapper.deleteSysUserPathById(id);
    }

    @Override
    public void dealUserPath(String corpId,String userId)
    {

    }

    @Override
    public void dealOnlyDeptUserPath(String corpId,Long deId)
    {

    }
}
