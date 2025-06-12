package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.Setting;
import org.springframework.stereotype.Repository;

/**
 * 公司配置Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2024-04-19
 */
@Repository
public interface SettingMapper 
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
     * 删除公司配置
     * 
     * @param id 公司配置主键
     * @return 结果
     */
    public int deleteSettingById(Long id);

    /**
     * 批量删除公司配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSettingByIds(Long[] ids);
}
