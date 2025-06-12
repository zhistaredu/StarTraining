package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.system.domain.Banner;

/**
 * banner图Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-20
 */
public interface IBannerService 
{
    /**
     * 查询banner图
     * 
     * @param conditions banner图主键
     * @return banner图
     */
    public Banner selectBannerById(Map<String,Object> conditions);

    /**
     * 查询banner图列表
     * 
     * @param banner banner图
     * @return banner图集合
     */
    public List<Banner> selectBannerList(Banner banner);

    /**
     * 新增banner图
     * 
     * @param banner banner图
     * @return 结果
     */
    public int insertBanner(Banner banner);

    /**
     * 修改banner图
     * 
     * @param banner banner图
     * @return 结果
     */
    public int updateBanner(Banner banner);

    /**
     * 批量删除banner图
     * 
     * @param ids 需要删除的banner图主键集合
     * @return 结果
     */
    public int deleteBannerByIds(Long[] ids);

    /**
     * 删除banner图信息
     * 
     * @param id banner图主键
     * @return 结果
     */
    public int deleteBannerById(Long id);
}
