package com.edu.knowledge.mapper;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.Courseware;
import com.edu.knowledge.domain.vo.CoursewareJoinUser;
import com.edu.statistics.domain.vo.LessonTypeCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 课程课件表Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Repository
public interface CoursewareMapper
{
    /**
     * 查询课程课件表
     * 
     * @param conditions 课程课件表主键
     * @return 课程课件表
     */
    public Courseware selectCoursewareById(Map<String,Object> conditions);

    /**
     * 查询课程课件表列表
     * 
     * @param conditions 课程课件表
     * @return 课程课件表集合
     */
    public List<Courseware> selectCoursewareList(Map<String,Object> conditions);

    public List<CoursewareJoinUser> selectCoursewareJoinUserList(Map<String,Object> conditions);

    public int selectLessonInfoWeight(Map<String,Object> conditions);

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

    public int insertCoursewareAll(@Param("insertData") List insertData);

    /**
     * 修改课程课件表
     * 
     * @param courseware 课程课件表
     * @return 结果
     */
    public int updateCourseware(Courseware courseware);

    /**
     * 删除课程课件表
     * 
     * @param conditions 课程课件表主键
     * @return 结果
     */
    public int deleteCoursewareById(Map<String,Object> conditions);

}
