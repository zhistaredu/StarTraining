package com.edu.web.controller.system;

import java.util.*;

import com.edu.common.config.OssConfig;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.system.domain.vo.BannerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.annotation.Log;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.enums.BusinessType;
import com.edu.system.domain.Banner;
import com.edu.system.service.IBannerService;

/**
 * banner图Controller
 * 
 * @author chuanzhiedu
 * @date 2023-10-20
 */
@RestController
@RequestMapping("/system/banner")
public class BannerController extends BaseController
{
    @Autowired
    private IBannerService bannerService;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 查询banner图列表
     */
    @GetMapping("/info")
    public AjaxResult info()
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("isDelete",0);
        conditions.put("companyId",userInfo.getCompanyId());
        Banner info = bannerService.selectBannerById(conditions);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",info);
        return ajax;
    }

    /**
     * 新增banner图
     */
    @Log(title = "banner图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Banner banner)
    {
        return toAjax(bannerService.insertBanner(banner));
    }

    /**
     * 查询banner图列表
     */
    @GetMapping("/home/list")
    public AjaxResult list()
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> where = new HashMap<>();
        where.put("companyId",companyId);
        Banner info = bannerService.selectBannerById(where);
        List<BannerVo> group = new ArrayList<>();
        if (info == null){
            BannerVo aa = new BannerVo();
            aa.setCdnImage(ossConfig.getCdnUrl() + "/ding3fff6f31979206c44ac5d6980864d335/course/image/AzeXWHczZTcYEjdizK277wTA83ibitbF1708059740849.png");
            group.add(aa);
        } else {
            group = info.getObjectGroup();
        }
        return AjaxResult.success(group);
    }
}
