package com.edu.knowledge.service;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.Courseware;
import com.edu.knowledge.domain.view.LessonView;
import com.edu.knowledge.domain.vo.CoursewareJoinUser;
import com.edu.statistics.domain.vo.LessonTypeCount;

/**
 * 课程课件表Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
public interface ICoursewareService
{
    /**
     * 查询课程课件表
     * 
     * @param conditions 课程课件表主键
     * @return 课程课件表
     */
    public Courseware selectCoursewareById(Map<String,Object> conditions);

    public LessonView getCoursewareInfo(Map<String,Object> conditions) throws Exception;

    public Map<String,Object> docPlayUrl(String accessToken,String refreshToken) throws Exception;

    public Courseware selectNextLesson(String courseId, int weight);

    /**
     * 查询课程课件表列表
     * 
     * @param conditions 课程课件表
     * @return 课程课件表集合
     */
    public List<Courseware> selectCoursewareList(Map<String,Object> conditions);

    public List<CoursewareJoinUser> selectCoursewareJoinUserList(Map<String,Object> conditions);

    public int selectCoursewareCount(Map<String,Object> conditions);

    public LessonTypeCount selectCoursewareTypeCount(Map<String,Object> conditions);

    public int selectCoursewareLength(Map<String,Object> conditions);

    /**
     * 新增课程课件表
     * 
     * @param courseware 课程课件表
     * @return 结果
     */
    public int insertCourseware(Courseware courseware);

    public int insertCoursewareInfo(Courseware courseware);

    /**
     * 批量插入课件
     *
     * @param courseware 批量插入课件
     * @return 结果
     */
    public int batchInsertCourseware(Courseware courseware);

    /**
     * 修改课程课件表
     * 
     * @param courseware 课程课件表
     * @return 结果
     */
    public int updateCourseware(Courseware courseware);

    public int updateCoursewareInfo(Courseware courseware);

    /**
     * 删除课程课件表信息
     * 
     * @param uuid 课程课件表主键
     * @return 结果
     */
    public int deleteCoursewareById(String uuid);

    public int CoursewareListSort(Map<String,Object> params);
}
