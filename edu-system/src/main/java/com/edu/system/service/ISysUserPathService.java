package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysUserPath;

/**
 * 用户路径Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
public interface ISysUserPathService 
{
    /**
     *
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

    /**
     * 修改用户路径
     * 
     * @param sysUserPath 用户路径
     * @return 结果
     */
    public int updateSysUserPath(SysUserPath sysUserPath);

    /**
     * 批量删除用户路径
     * 
     * @param ids 需要删除的用户路径主键集合
     * @return 结果
     */
    public int deleteSysUserPathByIds(Long[] ids);

    /**
     * 删除用户路径信息
     * 
     * @param id 用户路径主键
     * @return 结果
     */
    public int deleteSysUserPathById(Long id);

    public void dealUserPath(String corpId,String userId);

    public void dealOnlyDeptUserPath(String corpId,Long deptId);
}
