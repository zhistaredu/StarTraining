package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysUserPath;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户路径Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
@Repository
public interface SysUserPathMapper 
{
    /**
     * 查询用户人数
     *
     * @param conditions 用户路径主键
     * @return 用户路径
     */
    public Integer selectUserPathCount(Map<String,Object> conditions);

    /**
     * 查询用户路径
     * 
     * @param conditions 用户路径主键
     * @return 用户路径
     */
    public SysUserPath selectSysUserPathById(Map<String,Object> conditions);

    /**
     * 查询用户路径列表
     * 
     * @param conditions 用户路径
     * @return 用户路径集合
     */
    public List<SysUserPath> selectSysUserPathList(Map<String,Object> conditions);

    /**
     * 新增用户路径
     * 
     * @param sysUserPath 用户路径
     * @return 结果
     */
    public int insertSysUserPath(SysUserPath sysUserPath);

    public int insertSysUserPathAll(@Param("insertData") List insertData);

    /**
     * 修改用户路径
     * 
     * @param sysUserPath 用户路径
     * @return 结果
     */
    public int updateSysUserPath(SysUserPath sysUserPath);

    public int updateSysUserPathStatus(SysUserPath sysUserPath);

    /**
     * 删除用户路径
     * 
     * @param id 用户路径主键
     * @return 结果
     */
    public int deleteSysUserPathById(Long id);

    /**
     * 批量删除用户路径
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserPathByIds(Long[] ids);
}
