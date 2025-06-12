package com.edu.knowledge.mapper;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.Knowledge;
import com.edu.knowledge.domain.vo.KnowledgeTypeCount;
import org.springframework.stereotype.Repository;

/**
 * 课程主表Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Repository
public interface KnowledgeMapper
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
     * 删除课程主表
     * 
     * @param id 课程主表主键
     * @return 结果
     */
    public int deleteCourseById(Long id);

    /**
     * 批量删除课程主表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseByIds(Long[] ids);
}
