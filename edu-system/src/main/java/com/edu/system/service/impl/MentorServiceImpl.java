package com.edu.system.service.impl;

import java.util.List;
import java.util.Map;

import com.edu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.MentorMapper;
import com.edu.system.domain.Mentor;
import com.edu.system.service.IMentorService;

/**
 * 导师Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
@Service
public class MentorServiceImpl implements IMentorService 
{
    @Autowired
    private MentorMapper mentorMapper;

    /**
     * 查询导师
     * 
     * @param conditions 导师主键
     * @return 导师
     */
    @Override
    public Mentor selectMentorById(Map<String,Object> conditions)
    {
        return mentorMapper.selectMentorById(conditions);
    }

    /**
     * 查询导师列表
     * 
     * @param conditions 导师
     * @return 导师
     */
    @Override
    public List<Mentor> selectMentorList(Map<String,Object> conditions)
    {
        return mentorMapper.selectMentorList(conditions);
    }

    /**
     * 新增导师
     * 
     * @param mentor 导师
     * @return 结果
     */
    @Override
    public int insertMentor(Mentor mentor)
    {
        mentor.setCreateTime(DateUtils.getTime());
        return mentorMapper.insertMentor(mentor);
    }

    /**
     * 修改导师
     * 
     * @param mentor 导师
     * @return 结果
     */
    @Override
    public int updateMentor(Mentor mentor)
    {
        mentor.setUpdateTime(DateUtils.getTime());
        return mentorMapper.updateMentor(mentor);
    }

    /**
     * 批量删除导师
     * 
     * @param ids 需要删除的导师主键
     * @return 结果
     */
    @Override
    public int deleteMentorByIds(Long[] ids)
    {
        return mentorMapper.deleteMentorByIds(ids);
    }

    /**
     * 删除导师信息
     * 
     * @param id 导师主键
     * @return 结果
     */
    @Override
    public int deleteMentorById(Long id)
    {
        return mentorMapper.deleteMentorById(id);
    }
}
