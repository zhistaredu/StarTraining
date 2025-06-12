package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysCompanyUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 公司管理员Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
@Repository
public interface SysCompanyUserMapper 
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

    public int insertSysCompanyUserAll(@Param("insertData") List insertData);

    /**
     * 修改公司管理员
     * 
     * @param sysCompanyUser 公司管理员
     * @return 结果
     */
    public int updateSysCompanyUser(SysCompanyUser sysCompanyUser);

    /**
     * 删除公司管理员
     * 
     * @param id 公司管理员主键
     * @return 结果
     */
    public int deleteSysCompanyUserById(Long id);

    /**
     * 批量删除公司管理员
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCompanyUserByIds(Long[] ids);
}
