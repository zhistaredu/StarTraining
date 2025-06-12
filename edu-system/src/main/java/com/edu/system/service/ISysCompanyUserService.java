package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysCompanyUser;
/**
 * 公司管理员Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
public interface ISysCompanyUserService 
{
    /**
     * 查询公司管理员
     * 
     * @param conditions 公司管理员主键
     * @return 公司管理员
     */
    public SysCompanyUser selectSysCompanyUserById(Map<String,Object> conditions);

    /**
     * 查询公司管理员列表
     * 
     * @param sysCompanyUser 公司管理员
     * @return 公司管理员集合
     */
    public List<SysCompanyUser> selectSysCompanyUserList(SysCompanyUser sysCompanyUser);

    /**
     * 新增公司管理员
     * 
     * @param sysCompanyUser 公司管理员
     * @return 结果
     */
    public int insertSysCompanyUser(SysCompanyUser sysCompanyUser);

    /**
     * 修改公司管理员
     * 
     * @param sysCompanyUser 公司管理员
     * @return 结果
     */
    public int updateSysCompanyUser(SysCompanyUser sysCompanyUser);

    /**
     * 批量删除公司管理员
     * 
     * @param ids 需要删除的公司管理员主键集合
     * @return 结果
     */
    public int deleteSysCompanyUserByIds(Long[] ids);

    /**
     * 删除公司管理员信息
     * 
     * @param id 公司管理员主键
     * @return 结果
     */
    public int deleteSysCompanyUserById(Long id);

    public void buildCompanyUser(String corpId,String authUserId,String agentId);
}
