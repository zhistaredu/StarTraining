package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.Picture;

/**
 * 图片管理Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-27
 */
public interface IPictureService 
{
    /**
     * 查询图片管理
     * 
     * @param conditions 图片管理主键
     * @return 图片管理
     */
    public Picture selectPictureById(Map<String,Object> conditions);

    /**
     * 查询图片管理列表
     * 
     * @param conditions 图片管理
     * @return 图片管理集合
     */
    public List<Picture> selectPictureList(Map<String,Object> conditions);

    public int selectPictureCount(Map<String,Object> conditions);

    /**
     * 新增图片管理
     * 
     * @param picture 图片管理
     * @return 结果
     */
    public int insertPicture(Picture picture);

    public void initializePicture(String companyId);

    /**
     * 批量删除图片管理
     * 
     * @param ids 需要删除的图片管理主键集合
     * @return 结果
     */
    public int deletePictureByIds(String[] ids);

    /**
     * 删除图片管理信息
     * 
     * @param uuid 图片管理主键
     * @return 结果
     */
    public int deletePictureById(String uuid);
}
