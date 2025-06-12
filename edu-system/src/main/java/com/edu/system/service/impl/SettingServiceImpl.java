package com.edu.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.SettingMapper;
import com.edu.system.domain.Setting;
import com.edu.system.service.ISettingService;

/**
 * 公司配置Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2024-04-19
 */
@Service
public class SettingServiceImpl implements ISettingService 
{
    @Autowired
    private SettingMapper settingMapper;

    /**
     * 查询公司配置
     * 
     * @param conditions 公司配置主键
     * @return 公司配置
     */
    @Override
    public Setting selectSettingById(Map<String,Object> conditions)
    {
        return settingMapper.selectSettingById(conditions);
    }

    /**
     * 查询公司配置列表
     * 
     * @param conditions 公司配置
     * @return 公司配置
     */
    @Override
    public List<Setting> selectSettingList(Map<String,Object> conditions)
    {
        return settingMapper.selectSettingList(conditions);
    }

    /**
     * 新增公司配置
     * 
     * @param setting 公司配置
     * @return 结果
     */
    @Override
    public int insertSetting(Setting setting)
    {
        return settingMapper.insertSetting(setting);
    }

    /**
     * 修改公司配置
     * 
     * @param setting 公司配置
     * @return 结果
     */
    @Override
    public int updateSetting(Setting setting)
    {
        return settingMapper.updateSetting(setting);
    }

    /**
     * 批量删除公司配置
     * 
     * @param ids 需要删除的公司配置主键
     * @return 结果
     */
    @Override
    public int deleteSettingByIds(Long[] ids)
    {
        return settingMapper.deleteSettingByIds(ids);
    }

    /**
     * 删除公司配置信息
     * 
     * @param id 公司配置主键
     * @return 结果
     */
    @Override
    public int deleteSettingById(Long id)
    {
        return settingMapper.deleteSettingById(id);
    }
}
