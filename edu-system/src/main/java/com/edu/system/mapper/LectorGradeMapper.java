package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.LectorGrade;
import org.springframework.stereotype.Repository;

/**
 * 讲师等级Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
@Repository
public interface LectorGradeMapper 
{
    /**
     * 查询讲师等级
     * 
     * @param conditions 讲师等级主键
     * @return 讲师等级
     */
    public LectorGrade selectLectorGradeById(Map<String,Object> conditions);

    /**
     * 查询讲师等级列表
     * 
     * @param conditions 讲师等级
     * @return 讲师等级集合
     */
    public List<LectorGrade> selectLectorGradeList(Map<String,Object> conditions);

    /**
     * 新增讲师等级
     * 
     * @param lectorGrade 讲师等级
     * @return 结果
     */
    public int insertLectorGrade(LectorGrade lectorGrade);

    /**
     * 修改讲师等级
     * 
     * @param lectorGrade 讲师等级
     * @return 结果
     */
    public int updateLectorGrade(LectorGrade lectorGrade);

    /**
     * 删除讲师等级
     * 
     * @param id 讲师等级主键
     * @return 结果
     */
    public int deleteLectorGradeById(Long id);

    /**
     * 批量删除讲师等级
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLectorGradeByIds(Long[] ids);
}
