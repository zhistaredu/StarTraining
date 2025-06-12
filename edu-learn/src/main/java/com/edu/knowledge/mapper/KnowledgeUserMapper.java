package com.edu.knowledge.mapper;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.KnowledgeUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 课程学员表Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Repository
public interface KnowledgeUserMapper
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

    /**
     * 查询课程学员表列表
     * 
     * @param conditions 课程学员表
     * @return 课程学员表集合
     */
    public List<KnowledgeUser> selectCourseUserList(Map<String,Object> conditions);

    /**
     * 新增课程学员表
     * 
     * @param knowledgeUser 课程学员表
     * @return 结果
     */
    public int insertCourseUser(KnowledgeUser knowledgeUser);

    public int insertCourseUserAll(@Param("insertData") List courseUser);

    /**
     * 修改课程学员表
     * 
     * @param knowledgeUser 课程学员表
     * @return 结果
     */
    public int updateCourseUser(KnowledgeUser knowledgeUser);

    public int deleteCourseUser(KnowledgeUser knowledgeUser);

    /**
     * 删除课程学员表
     * 
     * @param id 课程学员表主键
     * @return 结果
     */
    public int deleteCourseUserById(Long id);

    /**
     * 批量删除课程学员表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseUserByIds(Long[] ids);
}
