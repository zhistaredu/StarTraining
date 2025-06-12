package com.edu.knowledge.service;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.KnowledgeUser;
import com.edu.knowledge.domain.CoursewareLearn;
import com.edu.statistics.domain.CourseDeptReport;
import com.edu.statistics.domain.CourseUserReport;

/**
 * 课程学员表Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
public interface IKnowledgeUserService
{
    /**
     * 查询课程学员表
     * 
     * @param conditions 课程学员表主键
     * @return 课程学员表
     */
    public KnowledgeUser selectCourseUserById(Map<String,Object> conditions);

    public int selectCourseUserCount(Map<String,Object> conditions);

    public int selectCourseUserSum(Map<String,Object> conditions);

    public KnowledgeUser joinCourseUser(String targetId, String from, String sourceId);

    /**
     * 查询课程学员表列表
     * 
     * @param conditions 课程学员表
     * @return 课程学员表集合
     */
    public List<KnowledgeUser> selectCourseUserList(Map<String,Object> conditions);

    public List<CourseUserReport> getCourseUserStatistics(Map<String,Object> conditions);

    public List<CourseDeptReport>  getCourseDeptStatistics(Map<String,Object> conditions);

    public void syncCourseUserData(CoursewareLearn learnInfo);

    /**
     * 新增课程学员表
     * 
     * @param knowledgeUser 课程学员表
     * @return 结果
     */
    public int insertCourseUser(KnowledgeUser knowledgeUser);

    /**
     * 新增课程人员
     *
     * @param courseUser 课程人员
     * @return 结果
     */
    public int insertCourseUserAll(List courseUser);

    /**
     * 修改课程学员表
     * 
     * @param knowledgeUser 课程学员表
     * @return 结果
     */
    public int updateCourseUser(KnowledgeUser knowledgeUser);

    public int deleteCourseUser(KnowledgeUser knowledgeUser);

    /**
     * 批量删除课程学员表
     * 
     * @param ids 需要删除的课程学员表主键集合
     * @return 结果
     */
    public int deleteCourseUserByIds(Long[] ids);

    /**
     * 删除课程学员表信息
     * 
     * @param id 课程学员表主键
     * @return 结果
     */
    public int deleteCourseUserById(Long id);
}
