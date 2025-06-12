package com.edu.knowledge.service;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.CreditLog;
import com.edu.knowledge.domain.vo.CreditRanking;

/**
 * 用户学分记录Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-11-13
 */
public interface ICreditLogService 
{
    /**
     * 查询用户学分记录
     * 
     * @param conditions 用户学分记录主键
     * @return 用户学分记录
     */
    public CreditLog selectCreditLogById(Map<String,Object> conditions);

    /**
     * 查询用户学分记录列表
     * 
     * @param conditions 用户学分记录
     * @return 用户学分记录集合
     */
    public List<CreditLog> selectCreditLogList(Map<String,Object> conditions);

    public List<CreditRanking> selectCreditRanking(Map<String,Object> conditions);

    public int selectCreditLogSum(Map<String,Object> conditions);

    /**
     * 新增用户学分记录
     * 
     * @param creditLog 用户学分记录
     * @return 结果
     */
    public int insertCreditLog(CreditLog creditLog);

    /**
     * 修改用户学分记录
     * 
     * @param creditLog 用户学分记录
     * @return 结果
     */
    public int updateCreditLog(CreditLog creditLog);

    /**
     * 批量删除用户学分记录
     * 
     * @param ids 需要删除的用户学分记录主键集合
     * @return 结果
     */
    public int deleteCreditLogByIds(Long[] ids);

    /**
     * 删除用户学分记录信息
     * 
     * @param id 用户学分记录主键
     * @return 结果
     */
    public int deleteCreditLogById(Long id);
}
