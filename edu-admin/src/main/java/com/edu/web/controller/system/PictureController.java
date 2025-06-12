package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.edu.common.config.OssConfig;
import com.edu.common.core.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.annotation.Log;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.enums.BusinessType;
import com.edu.system.domain.Picture;
import com.edu.system.service.IPictureService;
import com.edu.common.core.page.TableDataInfo;

/**
 * 图片管理Controller
 * 
 * @author chuanzhiedu
 * @date 2023-10-27
 */
@RestController
@RequestMapping("/system/picture")
public class PictureController extends BaseController
{
    @Autowired
    private IPictureService pictureService;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 查询图片管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Picture picture)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("type",picture.getType());
        conditions.put("styleType",picture.getStyleType());
        conditions.put("companyId",companyId);
        startPage();
        List<Picture> list = pictureService.selectPictureList(conditions);
        for (Picture items : list){
            items.setCdnObject(ossConfig.getCdnUrl() + "/" + items.getObject());
        }
        return getDataTable(list);
    }


    /**
     * 新增图片管理
     */
    @Log(title = "图片管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Picture picture)
    {
        SysUser userInfo = getLoginUser();
        picture.setUuid(UUID.randomUUID().toString());
        picture.setStatus(1);
        picture.setIsDelete(0);
        picture.setCreateUserId(userInfo.getUserId());
        picture.setUpdateUserId(userInfo.getUserId());
        picture.setCompanyId(userInfo.getCompanyId());
        return toAjax(pictureService.insertPicture(picture));
    }

    /**
     * 删除图片管理
     */
    @Log(title = "图片管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(pictureService.deletePictureByIds(ids));
    }
}
