package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.Setting;

/**
 * 公司配置Service接口
 * 
 * @author chuanzhiedu
 * @date 2024-04-19
 */
public interface ISettingService 
{
    /**
     * 查询公司配置
     * 
     * @param conditions 公司配置主键
     * @return 公司配置
     */
    public Setting selectSettingById(Map<String,Object> conditions);

    /**
     * 查询公司配置列表
     * 
     * @param conditions 公司配置
     * @return 公司配置集合
     */
    public List<Setting> selectSettingList(Map<String,Object> conditions);

    /**
     * 新增公司配置
     * 
     * @param setting 公司配置
     * @return 结果
     */
    public int insertSetting(Setting setting);

    /**
     * 修改公司配置
     * 
     * @param setting 公司配置
     * @return 结果
     */
    public int updateSetting(Setting setting);

    /**
     * 批量删除公司配置
     * 
     * @param ids 需要删除的公司配置主键集合
     * @return 结果
     */
    public int deleteSettingByIds(Long[] ids);

    /**
     * 删除公司配置信息
     * 
     * @param id 公司配置主键
     * @return 结果
     */
    public int deleteSettingById(Long id);
}
