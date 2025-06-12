package com.edu.system.service.impl;

import java.util.List;
import java.util.Map;

import com.edu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.MentorGradeMapper;
import com.edu.system.domain.MentorGrade;
import com.edu.system.service.IMentorGradeService;

/**
 * 导师等级Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
@Service
public class MentorGradeServiceImpl implements IMentorGradeService 
{
    @Autowired
    private MentorGradeMapper mentorGradeMapper;

    /**
     * 查询导师等级
     * 
     * @param conditions 导师等级主键
     * @return 导师等级
     */
    @Override
    public MentorGrade selectMentorGradeById(Map<String,Object> conditions)
    {
        return mentorGradeMapper.selectMentorGradeById(conditions);
    }

    /**
     * 查询导师等级列表
     * 
     * @param conditions 导师等级
     * @return 导师等级
     */
    @Override
    public List<MentorGrade> selectMentorGradeList(Map<String,Object> conditions)
    {
        return mentorGradeMapper.selectMentorGradeList(conditions);
    }

    /**
     * 新增导师等级
     * 
     * @param mentorGrade 导师等级
     * @return 结果
     */
    @Override
    public int insertMentorGrade(MentorGrade mentorGrade)
    {
        mentorGrade.setCreateTime(DateUtils.getTime());
        return mentorGradeMapper.insertMentorGrade(mentorGrade);
    }

    /**
     * 修改导师等级
     * 
     * @param mentorGrade 导师等级
     * @return 结果
     */
    @Override
    public int updateMentorGrade(MentorGrade mentorGrade)
    {
        mentorGrade.setUpdateTime(DateUtils.getTime());
        return mentorGradeMapper.updateMentorGrade(mentorGrade);
    }

    /**
     * 批量删除导师等级
     * 
     * @param ids 需要删除的导师等级主键
     * @return 结果
     */
    @Override
    public int deleteMentorGradeByIds(Long[] ids)
    {
        return mentorGradeMapper.deleteMentorGradeByIds(ids);
    }

    /**
     * 删除导师等级信息
     * 
     * @param id 导师等级主键
     * @return 结果
     */
    @Override
    public int deleteMentorGradeById(Long id)
    {
        return mentorGradeMapper.deleteMentorGradeById(id);
    }
}
