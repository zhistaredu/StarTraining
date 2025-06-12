package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.Picture;
import org.springframework.stereotype.Repository;

/**
 * 图片管理Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-27
 */
@Repository
public interface PictureMapper 
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

    /**
     * 修改图片管理
     * 
     * @param picture 图片管理
     * @return 结果
     */
    public int updatePicture(Picture picture);

    /**
     * 删除图片管理
     * 
     * @param conditions 图片管理主键
     * @return 结果
     */
    public int deletePictureById(Map<String,Object> conditions);

    /**
     * 批量删除图片管理
     * 
     * @param conditions 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePictureByIds(Map<String,Object> conditions);
}
