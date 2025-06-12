package com.edu.system.service.impl;

import java.util.List;
import java.util.Map;

import com.edu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.MqJobMapper;
import com.edu.system.domain.MqJob;
import com.edu.system.service.IMqJobService;

/**
 * 执行任务Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-01-24
 */
@Service
public class MqJobServiceImpl implements IMqJobService
{
    @Autowired
    private MqJobMapper mqJobMapper;


    /**
     * 查询执行任务
     * 
     * @param conditions 执行任务主键
     * @return 执行任务
     */
    @Override
    public MqJob selectMqJobById(Map<String,Object> conditions)
    {
        return mqJobMapper.selectMqJobById(conditions);
    }

    /**
     * 查询执行任务列表
     * 
     * @param mqJob 执行任务
     * @return 执行任务
     */
    @Override
    public List<MqJob> selectMqJobList(MqJob mqJob)
    {
        return mqJobMapper.selectMqJobList(mqJob);
    }

    /**
     * 新增执行任务
     * 
     * @param mqJob 执行任务
     * @return 结果
     */
    @Override
    public int insertMqJob(MqJob mqJob)
    {
        mqJob.setActionNum(1);
        mqJobMapper.insertMqJob(mqJob);
        return 1;
    }

    /**
     * 修改执行任务
     * 
     * @param mqJob 执行任务
     * @return 结果
     */
    @Override
    public int updateMqJob(MqJob mqJob)
    {
        mqJob.setUpdateTime(DateUtils.getTime());
        return mqJobMapper.updateMqJob(mqJob);
    }

    /**
     * 批量删除执行任务
     * 
     * @param ids 需要删除的执行任务主键
     * @return 结果
     */
    @Override
    public int deleteMqJobByIds(Long[] ids)
    {
        return mqJobMapper.deleteMqJobByIds(ids);
    }

    /**
     * 删除执行任务信息
     * 
     * @param id 执行任务主键
     * @return 结果
     */
    @Override
    public int deleteMqJobById(Long id)
    {
        return mqJobMapper.deleteMqJobById(id);
    }
}
