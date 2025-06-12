package com.edu.service;

import com.edu.domain.*;

import java.util.List;
import java.util.Map;

/**
 * 数据可见范围Service接口
 *
 * @author chuanzhiedu
 * @date 2023-01-23
 */
public interface IControlLimitService
{
    /**
     * 查询数据可见范围
     *
     * @param id 数据可见范围主键
     * @return 数据可见范围
     */
    public ControlLimit selectVisibleHomeById(Long id);

    /**
     * 查询数据可见范围列表
     *
     * @param conditions 数据可见范围
     * @return 数据可见范围集合
     */
    public List<ControlLimit> selectVisibleHomeList(Map<String,Object> conditions);

    public Map<String, List<ControlLimit>> getVisibleScopeList(Map<String,Object> conditions);

    public Map<String,Object> selectVisibleHomeManageList(Map<String,Object> conditions);

    public List<KnowledgeControlLimit> selectVisibleCourseHomeList(Map<String,Object> conditions);

    public List<ControlLimitScope> selectVisibleList(String companyId,String table);

    public KnowledgeControlLimit selectVisibleCourseHomeInfo(Map<String,Object> conditions);

    /**
     * 查询数据可见范围列表
     *
     * @param conditions 数据可见范围
     * @return 数据可见范围集合
     */
    public int selectVisibleHomeCount(Map<String,Object> conditions);

    public int selectVisibleHomeJoinCount(Map<String,Object> conditions);

    /**
     * 新增数据可见范围
     *
     * @param assignUserData 数据可见范围
     * @return 结果
     */
    public void insertVisibleHome(Map<String,Object> assignUserData);

    public int insertVisibleHomeAll(List<ControlLimit> insertData, String table);

    public void publishVisibleHome(String targetId,String targetTitle);

    /**
     * 修改数据可见范围
     *
     * @param controlLimit 数据可见范围
     * @return 结果
     */
    public int updateVisibleHome(ControlLimit controlLimit);

    /**
     * 批量删除数据可见范围
     *
     * @param ids 需要删除的数据可见范围主键集合
     * @return 结果
     */
    public int deleteVisibleHomeByIds(Long[] ids);

    /**
     * 删除数据可见范围信息
     *
     * @param conditions 数据可见范围主键
     * @return 结果
     */
    public int deleteVisibleHomeById(Map<String,Object> conditions);
}
