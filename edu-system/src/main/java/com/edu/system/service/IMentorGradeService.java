package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.MentorGrade;

/**
 * 导师等级Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
public interface IMentorGradeService 
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
     * 批量删除导师等级
     * 
     * @param ids 需要删除的导师等级主键集合
     * @return 结果
     */
    public int deleteMentorGradeByIds(Long[] ids);

    /**
     * 删除导师等级信息
     * 
     * @param id 导师等级主键
     * @return 结果
     */
    public int deleteMentorGradeById(Long id);
}
