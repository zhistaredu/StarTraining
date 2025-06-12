package com.edu.system.service.impl;

import java.util.List;

import com.edu.common.core.domain.entity.*;
import com.edu.common.utils.DateUtils;
import com.edu.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.SysCompanyMapper;

/**
 * 公司Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
@Service
public class SysCompanyServiceImpl implements ISysCompanyService 
{
    @Autowired
    private SysCompanyMapper sysCompanyMapper;

    /**
     * 查询公司
     * 
     * @param companyId 公司主键
     * @return 公司
     */
    @Override
    public SysCompany selectSysCompanyById(String companyId)
    {
        return sysCompanyMapper.selectSysCompanyById(companyId);
    }

    /**
     * 查询公司列表
     * 
     * @param sysCompany 公司
     * @return 公司
     */
    @Override
    public List<SysCompany> selectSysCompanyList(SysCompany sysCompany)
    {
        return sysCompanyMapper.selectSysCompanyList(sysCompany);
    }

    /**
     * 新增公司
     * 
     * @param sysCompany 公司
     * @return 结果
     */
    @Override
    public int insertSysCompany(SysCompany sysCompany)
    {
        sysCompany.setCreateTime(DateUtils.getTime());
        return sysCompanyMapper.insertSysCompany(sysCompany);
    }

    /**
     * 修改公司
     * 
     * @param sysCompany 公司
     * @return 结果
     */
    @Override
    public int updateSysCompany(SysCompany sysCompany)
    {
        sysCompany.setUpdateTime(DateUtils.getDate());
        return sysCompanyMapper.updateSysCompany(sysCompany);
    }

    /**
     * 批量删除公司
     * 
     * @param ids 需要删除的公司主键
     * @return 结果
     */
    @Override
    public int deleteSysCompanyByIds(Long[] ids)
    {
        return sysCompanyMapper.deleteSysCompanyByIds(ids);
    }

    /**
     * 删除公司信息
     * 
     * @param id 公司主键
     * @return 结果
     */
    @Override
    public int deleteSysCompanyById(Long id)
    {
        return sysCompanyMapper.deleteSysCompanyById(id);
    }

}
