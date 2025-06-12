package com.edu.knowledge.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.knowledge.domain.vo.CreditRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.knowledge.mapper.CreditLogMapper;
import com.edu.knowledge.domain.CreditLog;
import com.edu.knowledge.service.ICreditLogService;

/**
 * 用户学分记录Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-11-13
 */
@Service
public class CreditLogServiceImpl implements ICreditLogService 
{
    @Autowired
    private CreditLogMapper creditLogMapper;

    /**
     * 查询用户学分记录
     * 
     * @param conditions 用户学分记录主键
     * @return 用户学分记录
     */
    @Override
    public CreditLog selectCreditLogById(Map<String,Object> conditions)
    {
        return creditLogMapper.selectCreditLogById(conditions);
    }

    /**
     * 查询用户学分记录列表
     * 
     * @param conditions 用户学分记录
     * @return 用户学分记录
     */
    @Override
    public List<CreditLog> selectCreditLogList(Map<String,Object> conditions)
    {
        return creditLogMapper.selectCreditLogList(conditions);
    }

    @Override
    public List<CreditRanking> selectCreditRanking(Map<String,Object> conditions)
    {
        return creditLogMapper.selectCreditRanking(conditions);
    }

    @Override
    public int selectCreditLogSum(Map<String,Object> conditions)
    {
        return creditLogMapper.selectCreditLogSum(conditions);
    }

    /**
     * 新增用户学分记录
     * 
     * @param creditLog 用户学分记录
     * @return 结果
     */
    @Override
    public int insertCreditLog(CreditLog creditLog)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        creditLog.setUuid(UUID.randomUUID().toString());
        creditLog.setUserId(userInfo.getUserId());
        creditLog.setUserName(userInfo.getUserName());
        creditLog.setCreateTime(DateUtils.getTime());
        creditLog.setUpdateTime(DateUtils.getTime());
        creditLog.setIsDelete(0);
        creditLog.setCompanyId(userInfo.getCompanyId());
        return creditLogMapper.insertCreditLog(creditLog);
    }

    /**
     * 修改用户学分记录
     * 
     * @param creditLog 用户学分记录
     * @return 结果
     */
    @Override
    public int updateCreditLog(CreditLog creditLog)
    {
        return creditLogMapper.updateCreditLog(creditLog);
    }

    /**
     * 批量删除用户学分记录
     * 
     * @param ids 需要删除的用户学分记录主键
     * @return 结果
     */
    @Override
    public int deleteCreditLogByIds(Long[] ids)
    {
        return creditLogMapper.deleteCreditLogByIds(ids);
    }

    /**
     * 删除用户学分记录信息
     * 
     * @param id 用户学分记录主键
     * @return 结果
     */
    @Override
    public int deleteCreditLogById(Long id)
    {
        return creditLogMapper.deleteCreditLogById(id);
    }
}
