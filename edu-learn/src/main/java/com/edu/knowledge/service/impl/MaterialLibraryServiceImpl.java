package com.edu.knowledge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysCompany;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.system.service.ISysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.knowledge.mapper.MaterialLibraryMapper;
import com.edu.knowledge.domain.MaterialLibrary;
import com.edu.knowledge.service.IMaterialLibraryService;

/**
 * 素材库Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-10-14
 */
@Service
public class MaterialLibraryServiceImpl implements IMaterialLibraryService
{
    @Autowired
    private MaterialLibraryMapper materialLibraryMapper;

    @Autowired
    private ISysCompanyService sysCompanyService;

    /**
     * 查询素材库
     * 
     * @param conditions 素材库主键
     * @return 素材库
     */
    @Override
    public MaterialLibrary selectMaterialLibraryById(Map<String,Object> conditions)
    {
        return materialLibraryMapper.selectMaterialLibraryById(conditions);
    }

    /**
     * 查询素材库列表
     * 
     * @param conditions 素材库
     * @return 素材库
     */
    @Override
    public List<MaterialLibrary> selectMaterialLibraryList(Map<String,Object> conditions)
    {
        return materialLibraryMapper.selectMaterialLibraryList(conditions);
    }

    /**
     * 新增素材库
     * 
     * @param materialLibrary 素材库
     * @return 结果
     */
    @Override
    public int insertMaterialLibrary(MaterialLibrary materialLibrary) throws Exception {
        String companyId = materialLibrary.getCompanyId();
        materialLibrary.setCreateTime(DateUtils.getTime());
        materialLibrary.setUpdateTime(DateUtils.getTime());
        String type = materialLibrary.getType();
        //判断文档课件如果学习时长为0设置默认值300秒
        if(type.equals("document") && materialLibrary.getLength() == 0) {
            materialLibrary.setLength(300);
        }
        materialLibrary.setTransCodeStatus(1);//暂时验证使用
        materialLibraryMapper.insertMaterialLibrary(materialLibrary);
        SysCompany company = new SysCompany();
        company.setUsedSpace(materialLibrary.getLength());
        company.setUpdateTime(DateUtils.getTime());
        company.setCompanyId(companyId);
        sysCompanyService.updateSysCompany(company);
        return 1;
    }

    @Override
    public int insertMaterialLibraryInfo(MaterialLibrary materialLibrary) {
        return materialLibraryMapper.insertMaterialLibrary(materialLibrary);
    }

    @Override
    public void updateMaterialLibraryInfo(MaterialLibrary materialLibrary)
    {
        materialLibraryMapper.updateMaterialLibrary(materialLibrary);
    }

    /**
     * 修改素材库
     * 
     * @param materialLibrary 素材库
     * @return 结果
     */
    @Override
    public int updateMaterialLibrary(MaterialLibrary materialLibrary)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        materialLibrary.setUpdateTime(DateUtils.getTime());
        materialLibrary.setCompanyId(userInfo.getCompanyId());
        return materialLibraryMapper.updateMaterialLibrary(materialLibrary);
    }

    /**
     * 批量删除素材库
     * 
     * @param ids 需要删除的素材库主键
     * @return 结果
     */
    @Override
    public int deleteMaterialLibraryByIds(Long[] ids)
    {
        return materialLibraryMapper.deleteMaterialLibraryByIds(ids);
    }

    /**
     * 删除素材库信息
     * 
     * @param uuid 素材库主键
     * @return 结果
     */
    @Override
    public int deleteMaterialLibraryById(String uuid)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        MaterialLibrary materialLibrary = new MaterialLibrary();
        materialLibrary.setUuid(uuid);
        materialLibrary.setIsDelete(1);
        materialLibrary.setUpdateTime(DateUtils.getTime());
        materialLibrary.setCompanyId(companyId);
        return materialLibraryMapper.updateMaterialLibrary(materialLibrary);
    }
}
