package com.edu.system.mapper;

import java.util.List;
import com.edu.common.core.domain.entity.SysCompany;
import org.springframework.stereotype.Repository;

/**
 * 公司Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
@Repository
public interface SysCompanyMapper 
{
    /**
     * 查询公司
     * 
     * @param companyId 公司主键
     * @return 公司
     */
    public SysCompany selectSysCompanyById(String companyId);

    /**
     * 查询公司列表
     * 
     * @param sysCompany 公司
     * @return 公司集合
     */
    public List<SysCompany> selectSysCompanyList(SysCompany sysCompany);

    /**
     * 新增公司
     * 
     * @param sysCompany 公司
     * @return 结果
     */
    public int insertSysCompany(SysCompany sysCompany);

    /**
     * 修改公司
     * 
     * @param sysCompany 公司
     * @return 结果
     */
    public int updateSysCompany(SysCompany sysCompany);

    /**
     * 删除公司
     * 
     * @param id 公司主键
     * @return 结果
     */
    public int deleteSysCompanyById(Long id);

    /**
     * 批量删除公司
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCompanyByIds(Long[] ids);
}
