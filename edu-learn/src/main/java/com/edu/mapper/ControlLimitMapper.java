package com.edu.mapper;

import com.edu.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据可见范围Mapper接口
 *
 * @author chuanzhiedu
 * @date 2023-01-23
 */
@Repository
public interface ControlLimitMapper
{
    /**
     * 查询数据可见范围
     *
     * @param id 数据可见范围主键
     * @return 数据可见范围
     */
    public ControlLimit selectVisibleHomeById(Long id);

    /**
     * 查询数据可见范围列表
     *
     * @param conditions 数据可见范围
     * @return 数据可见范围集合
     */
    public List<ControlLimit> selectVisibleHomeList(Map<String,Object> conditions);

    public List<ControlLimitScope> selectVisibleList(@Param("companyId") String companyId,@Param("table") String table);

    public List<KnowledgeControlLimit> selectVisibleCourseHomeList(Map<String,Object> conditions);

    public KnowledgeControlLimit selectVisibleCourseHomeInfo(Map<String,Object> conditions);

    /**
     * 查询数据可见范围列表
     *
     * @param conditions 数据可见范围
     * @return 数据可见范围集合
     */
    public int selectVisibleHomeCount(Map<String,Object> conditions);

    public int selectVisibleHomeJoinCount(Map<String,Object> conditions);

    /**
     * 新增数据可见范围
     *
     * @param insertData 数据可见范围
     * @return 结果
     */
    public int insertVisibleHomeAll(@Param("insertData") List<ControlLimit> insertData);

    /**
     * 修改数据可见范围
     *
     * @param controlLimit 数据可见范围
     * @return 结果
     */
    public int updateVisibleHome(ControlLimit controlLimit);

    /**
     * 删除数据可见范围
     *
     * @param conditions 数据可见范围主键
     * @return 结果
     */
    public int deleteVisibleHomeById(Map<String,Object> conditions);

    /**
     * 批量删除数据可见范围
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisibleHomeByIds(Long[] ids);
}
