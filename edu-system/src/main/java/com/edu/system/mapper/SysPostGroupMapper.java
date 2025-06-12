package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.SysPostGroup;
import org.springframework.stereotype.Repository;

/**
 * 岗位组Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@Repository
public interface SysPostGroupMapper 
{
    /**
     * 查询岗位组
     * 
     * @param conditions 岗位组主键
     * @return 岗位组
     */
    public SysPostGroup selectSysPostGroupById(Map<String,Object> conditions);

    /**
     * 查询岗位组列表
     * 
     * @param conditions 岗位组
     * @return 岗位组集合
     */
    public List<SysPostGroup> selectSysPostGroupList(Map<String,Object> conditions);

    /**
     * 新增岗位组
     * 
     * @param sysPostGroup 岗位组
     * @return 结果
     */
    public int insertSysPostGroup(SysPostGroup sysPostGroup);

    /**
     * 修改岗位组
     * 
     * @param sysPostGroup 岗位组
     * @return 结果
     */
    public int updateSysPostGroup(SysPostGroup sysPostGroup);

    /**
     * 删除岗位组
     * 
     * @param id 岗位组主键
     * @return 结果
     */
    public int deleteSysPostGroupById(Long id);

    /**
     * 批量删除岗位组
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysPostGroupByIds(Long[] ids);
}
