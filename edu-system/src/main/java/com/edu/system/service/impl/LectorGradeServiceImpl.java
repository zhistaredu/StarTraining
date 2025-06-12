package com.edu.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.LectorGradeMapper;
import com.edu.system.domain.LectorGrade;
import com.edu.system.service.ILectorGradeService;

/**
 * 讲师等级Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
@Service
public class LectorGradeServiceImpl implements ILectorGradeService 
{
    @Autowired
    private LectorGradeMapper lectorGradeMapper;

    /**
     * 查询讲师等级
     * 
     * @param conditions 讲师等级主键
     * @return 讲师等级
     */
    @Override
    public LectorGrade selectLectorGradeById(Map<String,Object> conditions)
    {
        return lectorGradeMapper.selectLectorGradeById(conditions);
    }

    /**
     * 查询讲师等级列表
     * 
     * @param conditions 讲师等级
     * @return 讲师等级
     */
    @Override
    public List<LectorGrade> selectLectorGradeList(Map<String,Object> conditions)
    {
        return lectorGradeMapper.selectLectorGradeList(conditions);
    }

    /**
     * 新增讲师等级
     * 
     * @param lectorGrade 讲师等级
     * @return 结果
     */
    @Override
    public int insertLectorGrade(LectorGrade lectorGrade)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        lectorGrade.setUuid(UUID.randomUUID().toString());
        lectorGrade.setCreateTime(DateUtils.getTime());
        lectorGrade.setCreateUserId(userInfo.getUserId());
        lectorGrade.setCompanyId(userInfo.getCompanyId());
        return lectorGradeMapper.insertLectorGrade(lectorGrade);
    }

    /**
     * 修改讲师等级
     * 
     * @param lectorGrade 讲师等级
     * @return 结果
     */
    @Override
    public int updateLectorGrade(LectorGrade lectorGrade)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        lectorGrade.setUpdateTime(DateUtils.getTime());
        lectorGrade.setUpdateUserId(userInfo.getUserId());
        lectorGrade.setCompanyId(userInfo.getCompanyId());
        return lectorGradeMapper.updateLectorGrade(lectorGrade);
    }

    /**
     * 批量删除讲师等级
     * 
     * @param ids 需要删除的讲师等级主键
     * @return 结果
     */
    @Override
    public int deleteLectorGradeByIds(Long[] ids)
    {
        return lectorGradeMapper.deleteLectorGradeByIds(ids);
    }

    /**
     * 删除讲师等级信息
     * 
     * @param id 讲师等级主键
     * @return 结果
     */
    @Override
    public int deleteLectorGradeById(Long id)
    {
        return lectorGradeMapper.deleteLectorGradeById(id);
    }
}
