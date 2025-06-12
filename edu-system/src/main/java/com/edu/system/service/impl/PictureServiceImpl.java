package com.edu.system.service.impl;

import java.util.*;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.PictureMapper;
import com.edu.system.domain.Picture;
import com.edu.system.service.IPictureService;

/**
 * 图片管理Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-10-27
 */
@Service
public class PictureServiceImpl implements IPictureService 
{
    @Autowired
    private PictureMapper pictureMapper;

    /**
     * 查询图片管理
     * 
     * @param conditions 图片管理主键
     * @return 图片管理
     */
    @Override
    public Picture selectPictureById(Map<String,Object> conditions)
    {
        return pictureMapper.selectPictureById(conditions);
    }

    /**
     * 查询图片管理列表
     * 
     * @param conditions 图片管理
     * @return 图片管理
     */
    @Override
    public List<Picture> selectPictureList(Map<String,Object> conditions)
    {
        return pictureMapper.selectPictureList(conditions);
    }

    @Override
    public int selectPictureCount(Map<String,Object> conditions)
    {
        return pictureMapper.selectPictureCount(conditions);
    }

    /**
     * 新增图片管理
     * 
     * @param picture 图片管理
     * @return 结果
     */
    @Override
    public int insertPicture(Picture picture)
    {
        picture.setCreateTime(DateUtils.getTime());
        return pictureMapper.insertPicture(picture);
    }

    @Override
    public void initializePicture(String companyId)
    {
        List<String> images = new ArrayList<>();
        images.add("image/imageone.jpg");
        images.add("image/imagetwo.jpg");
        images.add("image/imagethree.jpg");
        images.add("image/imagefour.jpg");
        images.add("image/imagefive.jpg");
        for (String img : images){
            Picture picture = new Picture();
            picture.setUuid(UUID.randomUUID().toString());
            picture.setType("course");
            picture.setObject(img);
            picture.setStatus(1);
            picture.setIsDelete(0);
            picture.setUpdateTime(DateUtils.getTime());
            picture.setCreateUserId(companyId);
            picture.setUpdateUserId(companyId);
            picture.setCompanyId(companyId);
            insertPicture(picture);
        }
    }


    /**
     * 批量删除图片管理
     * 
     * @param ids 需要删除的图片管理主键
     * @return 结果
     */
    @Override
    public int deletePictureByIds(String[] ids)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("ids",ids);
        conditions.put("companyId",userInfo.getCompanyId());
        return pictureMapper.deletePictureByIds(conditions);
    }

    /**
     * 删除图片管理信息
     * 
     * @param uuid 图片管理主键
     * @return 结果
     */
    @Override
    public int deletePictureById(String uuid)
    {
        SysUser userInfo = new SysUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",userInfo.getCompanyId());
        return pictureMapper.deletePictureById(conditions);
    }
}
