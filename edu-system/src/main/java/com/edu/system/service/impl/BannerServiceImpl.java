package com.edu.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.system.domain.vo.BannerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.BannerMapper;
import com.edu.system.domain.Banner;
import com.edu.system.service.IBannerService;

/**
 * banner图Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-10-20
 */
@Service
public class BannerServiceImpl implements IBannerService 
{
    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 查询banner图
     * 
     * @param conditions banner图主键
     * @return banner图
     */
    @Override
    public Banner selectBannerById(Map<String,Object> conditions)
    {
        Banner info = bannerMapper.selectBannerById(conditions);
        if (info != null){
            List<BannerVo> group = JSON.parseObject(info.getObject(), new TypeReference<List<BannerVo>>() {});
            for (BannerVo items : group){
                items.setCdnImage(items.getImage());
            }
            info.setObjectGroup(group);
        }
        return info;
    }

    /**
     * 查询banner图列表
     * 
     * @param banner banner图
     * @return banner图
     */
    @Override
    public List<Banner> selectBannerList(Banner banner)
    {
        return bannerMapper.selectBannerList(banner);
    }

    /**
     * 新增banner图
     * 
     * @param banner banner图
     * @return 结果
     */
    @Override
    public int insertBanner(Banner banner)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("isDelete",0);
        conditions.put("companyId",companyId);
        Banner info = selectBannerById(conditions);
        if (info == null){
            banner.setStatus(1);
            banner.setIsDelete(0);
            banner.setCreateTime(DateUtils.getTime());
            banner.setUpdateTime(DateUtils.getTime());
            banner.setCreateUserId(userId);
            banner.setUpdateUserId(userId);
            banner.setCompanyId(companyId);
            bannerMapper.insertBanner(banner);
        } else {
            banner.setUpdateTime(DateUtils.getTime());
            banner.setUpdateUserId(userId);
            banner.setCompanyId(companyId);
            bannerMapper.updateBanner(banner);
        }
        return 1;
    }

    /**
     * 修改banner图
     * 
     * @param banner banner图
     * @return 结果
     */
    @Override
    public int updateBanner(Banner banner)
    {
        banner.setUpdateTime(DateUtils.getTime());
        return bannerMapper.updateBanner(banner);
    }

    /**
     * 批量删除banner图
     * 
     * @param ids 需要删除的banner图主键
     * @return 结果
     */
    @Override
    public int deleteBannerByIds(Long[] ids)
    {
        return bannerMapper.deleteBannerByIds(ids);
    }

    /**
     * 删除banner图信息
     * 
     * @param id banner图主键
     * @return 结果
     */
    @Override
    public int deleteBannerById(Long id)
    {
        return bannerMapper.deleteBannerById(id);
    }
}
