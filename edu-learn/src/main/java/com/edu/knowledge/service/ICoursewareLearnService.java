package com.edu.knowledge.service;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.CoursewareLearn;
import com.edu.knowledge.domain.vo.LessonProgress;
import com.edu.statistics.domain.vo.LessonLearnScreenVo;

/**
 * 课程课件学习Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
public interface ICoursewareLearnService
{
    /**
     * 查询课程课件学习
     * 
     * @param conditions 课程课件学习主键
     * @return 课程课件学习
     */
    public CoursewareLearn selectCoursewareLearnById(Map<String,Object> conditions);

    public CoursewareLearn selectLastLearnInfo(Map<String,Object> conditions);

    public void startLessonLearn(Map<String,Object> conditions);

    public void finishLessonLearn(CoursewareLearn coursewareLearn);

    public CoursewareLearn updateCoursewareLearnProgress(LessonProgress lessonProgress);

    /**
     * 查询课程课件学习列表
     * 
     * @param conditions 课程课件学习
     * @return 课程课件学习集合
     */
    public List<CoursewareLearn> selectCoursewareLearnList(Map<String,Object> conditions);

    public LessonLearnScreenVo getLessonLearnScreenList(Map<String,Object> conditions);

    /**
     * 新增课程课件学习
     * 
     * @param coursewareLearn 课程课件学习
     * @return 结果
     */
    public int insertCoursewareLearnInfo(CoursewareLearn coursewareLearn);

    public int insertCoursewareLearn(CoursewareLearn coursewareLearn);

    /**
     * 修改课程课件学习
     * 
     * @param coursewareLearn 课程课件学习
     * @return 结果
     */
    public int updateCoursewareLearn(CoursewareLearn coursewareLearn);

    /**
     * 批量删除课程课件学习
     * 
     * @param ids 需要删除的课程课件学习主键集合
     * @return 结果
     */
    public int deleteCoursewareLearnByIds(Long[] ids);

    /**
     * 删除课程课件学习信息
     * 
     * @param id 课程课件学习主键
     * @return 结果
     */
    public int deleteCoursewareLearnById(Long id);
}
