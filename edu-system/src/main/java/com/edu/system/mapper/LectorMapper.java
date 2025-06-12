package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.Lector;
import org.springframework.stereotype.Repository;

/**
 * 讲师管理Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
@Repository
public interface LectorMapper 
{
    /**
     * 查询讲师管理
     * 
     * @param conditions 讲师管理主键
     * @return 讲师管理
     */
    public Lector selectLectorById(Map<String,Object> conditions);

    /**
     * 查询讲师管理列表
     * 
     * @param conditions 讲师管理
     * @return 讲师管理集合
     */
    public List<Lector> selectLectorList(Map<String,Object> conditions);

    /**
     * 新增讲师管理
     * 
     * @param lector 讲师管理
     * @return 结果
     */
    public int insertLector(Lector lector);

    /**
     * 修改讲师管理
     * 
     * @param lector 讲师管理
     * @return 结果
     */
    public int updateLector(Lector lector);

    /**
     * 删除讲师管理
     * 
     * @param id 讲师管理主键
     * @return 结果
     */
    public int deleteLectorById(Long id);

    /**
     * 批量删除讲师管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLectorByIds(Long[] ids);
}
