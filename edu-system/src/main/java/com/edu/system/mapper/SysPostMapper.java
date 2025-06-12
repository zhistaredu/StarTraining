package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.SysPost;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Repository;

/**
 * 岗位信息Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@Repository
public interface SysPostMapper 
{
    /**
     * 查询岗位信息
     * 
     * @param conditions 岗位信息主键
     * @return 岗位信息
     */
    public SysPost selectSysPostById(Map<String,Object> conditions);

    /**
     * 查询岗位信息列表
     * 
     * @param conditions 岗位信息
     * @return 岗位信息集合
     */
    public List<SysPost> selectSysPostList(Map<String,Object> conditions);

    public int selectSysPostCount(Map<String,Object> conditions);

    /**
     * 新增岗位信息
     * 
     * @param sysPost 岗位信息
     * @return 结果
     */
    public int insertSysPost(SysPost sysPost);

    /**
     * 修改岗位信息
     * 
     * @param sysPost 岗位信息
     * @return 结果
     */
    public int updateSysPost(SysPost sysPost);

    /**
     * 删除岗位信息
     * 
     * @param id 岗位信息主键
     * @return 结果
     */
    public int deleteSysPostById(Long id);

    /**
     * 批量删除岗位信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysPostByIds(Long[] ids);
}
