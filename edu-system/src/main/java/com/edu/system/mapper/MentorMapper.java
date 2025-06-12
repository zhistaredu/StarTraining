package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.Mentor;
import org.springframework.stereotype.Repository;

/**
 * 导师Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
@Repository
public interface MentorMapper 
{
    /**
     * 查询导师
     * 
     * @param conditions 导师主键
     * @return 导师
     */
    public Mentor selectMentorById(Map<String,Object> conditions);

    /**
     * 查询导师列表
     * 
     * @param conditions 导师
     * @return 导师集合
     */
    public List<Mentor> selectMentorList(Map<String,Object> conditions);

    /**
     * 新增导师
     * 
     * @param mentor 导师
     * @return 结果
     */
    public int insertMentor(Mentor mentor);

    /**
     * 修改导师
     * 
     * @param mentor 导师
     * @return 结果
     */
    public int updateMentor(Mentor mentor);

    /**
     * 删除导师
     * 
     * @param id 导师主键
     * @return 结果
     */
    public int deleteMentorById(Long id);

    /**
     * 批量删除导师
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMentorByIds(Long[] ids);
}
