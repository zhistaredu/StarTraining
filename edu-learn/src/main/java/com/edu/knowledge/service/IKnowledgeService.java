package com.edu.knowledge.service;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.Knowledge;
import com.edu.knowledge.domain.vo.KnowledgeTypeCount;

/**
 * 课程主表Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
public interface IKnowledgeService
{
    /**
     * 查询课程主表
     * 
     * @param conditions 课程主表主键
     * @return 课程主表
     */
    public Knowledge selectCourseById(Map<String,Object> conditions);

    /**
     * 查询课程主表列表
     * 
     * @param conditions 课程主表
     * @return 课程主表集合
     */
    public List<Knowledge> selectCourseList(Map<String,Object> conditions);

    public int selectCourseCount(Map<String,Object> conditions);

    public KnowledgeTypeCount selectCourseTypeCount(Map<String,Object> conditions);

    public KnowledgeTypeCount selectCourseStatusCount(Map<String,Object> conditions);

    public KnowledgeTypeCount selectCourseGroupCount(Map<String,Object> conditions);

    /**
     * 新增课程主表
     * 
     * @param knowledge 课程主表
     * @return 结果
     */
    public int insertCourse(Knowledge knowledge);

    /**
     * 修改课程主表
     * 
     * @param knowledge 课程主表
     * @return 结果
     */
    public int updateCourse(Knowledge knowledge);

    /**
     * 发布考试
     *
     * @param knowledge 课程主表
     * @return 结果
     */
    public void publishCourse(Knowledge knowledge);

    /**
     * 关闭考试
     *
     * @param knowledge 课程主表
     * @return 结果
     */
    public void closeCourse(Knowledge knowledge);

    /**
     * 删除课程信息
     *
     * @param id 考试主键
     * @return 结果
     */
    public void deleteCourseById(String id);

}
