package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.MentorGrade;
import org.springframework.stereotype.Repository;

/**
 * 导师等级Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
@Repository
public interface MentorGradeMapper 
{
    /**
     * 查询导师等级
     * 
     * @param conditions 导师等级主键
     * @return 导师等级
     */
    public MentorGrade selectMentorGradeById(Map<String,Object> conditions);

    /**
     * 查询导师等级列表
     * 
     * @param conditions 导师等级
     * @return 导师等级集合
     */
    public List<MentorGrade> selectMentorGradeList(Map<String,Object> conditions);

    /**
     * 新增导师等级
     * 
     * @param mentorGrade 导师等级
     * @return 结果
     */
    public int insertMentorGrade(MentorGrade mentorGrade);

    /**
     * 修改导师等级
     * 
     * @param mentorGrade 导师等级
     * @return 结果
     */
    public int updateMentorGrade(MentorGrade mentorGrade);

    /**
     * 删除导师等级
     * 
     * @param id 导师等级主键
     * @return 结果
     */
    public int deleteMentorGradeById(Long id);

    /**
     * 批量删除导师等级
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMentorGradeByIds(Long[] ids);
}
