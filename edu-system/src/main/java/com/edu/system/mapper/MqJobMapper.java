package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.MqJob;
import org.springframework.stereotype.Repository;

/**
 * 执行任务Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-01-24
 */
@Repository
public interface MqJobMapper
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
     * 删除执行任务
     * 
     * @param id 执行任务主键
     * @return 结果
     */
    public int deleteMqJobById(Long id);

    /**
     * 批量删除执行任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMqJobByIds(Long[] ids);
}
