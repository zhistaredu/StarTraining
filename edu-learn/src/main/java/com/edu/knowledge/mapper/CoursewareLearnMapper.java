package com.edu.knowledge.mapper;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.CoursewareLearn;
import com.edu.statistics.domain.vo.LessonLearnScreenVo;
import org.springframework.stereotype.Repository;

/**
 * 课程课件学习Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Repository
public interface CoursewareLearnMapper
{
    /**
     * 查询课程课件学习
     * 
     * @param conditions 课程课件学习主键
     * @return 课程课件学习
     */
    public CoursewareLearn selectCoursewareLearnById(Map<String,Object> conditions);

    public CoursewareLearn selectLastLearnInfo(Map<String,Object> conditions);

    public int selectCoursewareLearnCount(Map<String,Object> conditions);

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
    public int insertCoursewareLearn(CoursewareLearn coursewareLearn);

    /**
     * 修改课程课件学习
     * 
     * @param coursewareLearn 课程课件学习
     * @return 结果
     */
    public int updateCoursewareLearn(CoursewareLearn coursewareLearn);

    /**
     * 删除课程课件学习
     * 
     * @param id 课程课件学习主键
     * @return 结果
     */
    public int deleteCoursewareLearnById(Long id);

    /**
     * 批量删除课程课件学习
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCoursewareLearnByIds(Long[] ids);
}
