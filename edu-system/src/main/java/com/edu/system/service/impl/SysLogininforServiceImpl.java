package com.edu.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.edu.system.domain.vo.LoginInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.domain.SysLogininfor;
import com.edu.system.mapper.SysLogininforMapper;
import com.edu.system.service.ISysLogininforService;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author edu
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService
{

    @Autowired
    private SysLogininforMapper logininforMapper;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor)
    {
        logininfor.setInfoId(UUID.randomUUID().toString());
        logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor)
    {
        return logininforMapper.selectLogininforList(logininfor);
    }

    @Override
    public LoginInfoVo getLogininforCount(Map<String,Object> conditions)
    {
        return logininforMapper.getLogininforCount(conditions);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    @Override
    public int deleteLogininforByIds(String[] infoIds)
    {
        return logininforMapper.deleteLogininforByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor()
    {
        logininforMapper.cleanLogininfor();
    }
}
