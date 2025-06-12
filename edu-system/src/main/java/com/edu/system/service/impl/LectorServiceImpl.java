package com.edu.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.LectorMapper;
import com.edu.system.domain.Lector;
import com.edu.system.service.ILectorService;

/**
 * 讲师管理Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
@Service
public class LectorServiceImpl implements ILectorService 
{
    @Autowired
    private LectorMapper lectorMapper;

    /**
     * 查询讲师管理
     * 
     * @param conditions 讲师管理主键
     * @return 讲师管理
     */
    @Override
    public Lector selectLectorById(Map<String,Object> conditions)
    {
        return lectorMapper.selectLectorById(conditions);
    }

    /**
     * 查询讲师管理列表
     * 
     * @param conditions 讲师管理
     * @return 讲师管理
     */
    @Override
    public List<Lector> selectLectorList(Map<String,Object> conditions)
    {
        return lectorMapper.selectLectorList(conditions);
    }

    /**
     * 新增讲师管理
     * 
     * @param lector 讲师管理
     * @return 结果
     */
    @Override
    public int insertLector(Lector lector)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String lectorId = lector.getLectorId();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("lectorId",lectorId);
        conditions.put("companyId",companyId);
        Lector info = selectLectorById(conditions);
        if (info == null){
            lector.setCreateTime(DateUtils.getTime());
            lector.setCreateUserId(userInfo.getUserId());
            lector.setCompanyId(companyId);
            lector.setStatus(1);
            lectorMapper.insertLector(lector);
        }
        return 1;
    }

    /**
     * 修改讲师管理
     * 
     * @param lector 讲师管理
     * @return 结果
     */
    @Override
    public int updateLector(Lector lector)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String lectorId = lector.getLectorId();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("lectorId",lectorId);
        conditions.put("companyId",companyId);
        Lector info = selectLectorById(conditions);
        if (info != null){
            info.setUpdateTime(DateUtils.getTime());
            info.setUpdateUserId(userInfo.getUserId());
            lectorMapper.updateLector(info);
        }
        return 1;
    }

    @Override
    public void publishLector(Lector lector)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        lector.setStatus(1);
        lector.setCompanyId(companyId);
        lector.setUpdateTime(DateUtils.getTime());
        lectorMapper.updateLector(lector);
    }

    @Override
    public void closeLector(Lector lector)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        lector.setStatus(2);
        lector.setCompanyId(companyId);
        lector.setUpdateTime(DateUtils.getTime());
        lectorMapper.updateLector(lector);
    }

    /**
     * 批量删除讲师管理
     * 
     * @param ids 需要删除的讲师管理主键
     * @return 结果
     */
    @Override
    public int deleteLectorByIds(Long[] ids)
    {
        return lectorMapper.deleteLectorByIds(ids);
    }

    /**
     * 删除讲师管理信息
     * 
     * @param id 讲师管理主键
     * @return 结果
     */
    @Override
    public void deleteLectorById(String id)
    {
        Lector lector = new Lector();
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        String userId = userInfo.getUserId();
        lector.setLectorId(id);
        lector.setIsDelete(1);
        lector.setCompanyId(companyId);
        lector.setUpdateTime(DateUtils.getTime());
        lector.setUpdateUserId(userId);
        lectorMapper.updateLector(lector);
    }
}
