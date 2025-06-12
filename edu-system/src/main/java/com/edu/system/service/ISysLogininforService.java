package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.SysLogininfor;
import com.edu.system.domain.vo.LoginInfoVo;

/**
 * 系统访问日志情况信息 服务层
 * 
 * @author edu
 */
public interface ISysLogininforService
{
    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLogininfor logininfor);

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    public LoginInfoVo getLogininforCount(Map<String,Object> conditions);

    /**
     * 批量删除系统登录日志
     * 
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    public int deleteLogininforByIds(String[] infoIds);

    /**
     * 清空系统登录日志
     */
    public void cleanLogininfor();
}
