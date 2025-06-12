package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.Lector;

/**
 * 讲师管理Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
public interface ILectorService 
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

    public void publishLector(Lector lector);

    public void closeLector(Lector lector);

    /**
     * 批量删除讲师管理
     * 
     * @param ids 需要删除的讲师管理主键集合
     * @return 结果
     */
    public int deleteLectorByIds(Long[] ids);

    /**
     * 删除讲师管理信息
     * 
     * @param id 讲师管理主键
     * @return 结果
     */
    public void deleteLectorById(String id);
}
