package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.MqJob;

/**
 * 执行任务Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-01-24
 */
public interface IMqJobService
{
    /**
     * 查询执行任务
     * 
     * @param conditions 执行任务主键
     * @return 执行任务
     */
    public MqJob selectMqJobById(Map<String,Object> conditions);

    /**
     * 查询执行任务列表
     * 
     * @param mqJob 执行任务
     * @return 执行任务集合
     */
    public List<MqJob> selectMqJobList(MqJob mqJob);

    /**
     * 新增执行任务
     * 
     * @param mqJob 执行任务
     * @return 结果
     */
    public int insertMqJob(MqJob mqJob);

    /**
     * 修改执行任务
     * 
     * @param mqJob 执行任务
     * @return 结果
     */
    public int updateMqJob(MqJob mqJob);

    /**
     * 批量删除执行任务
     * 
     * @param ids 需要删除的执行任务主键集合
     * @return 结果
     */
    public int deleteMqJobByIds(Long[] ids);

    /**
     * 删除执行任务信息
     * 
     * @param id 执行任务主键
     * @return 结果
     */
    public int deleteMqJobById(Long id);
}
