package com.edu.web.controller.knowledge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson2.JSON;
import com.edu.common.core.domain.entity.Category;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.knowledge.domain.MaterialLibrary;
import com.edu.knowledge.service.IMaterialLibraryService;
import com.edu.system.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.annotation.Log;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.enums.BusinessType;
import com.edu.common.core.page.TableDataInfo;

/**
 * 素材库Controller
 * 
 * @author chuanzhiedu
 * @date 2023-10-14
 */
@RestController
@RequestMapping("/learn/lesson")
public class MaterialLibraryController extends BaseController
{
    @Autowired
    private IMaterialLibraryService materialLibraryService;

    @Autowired
    private com.edu.config.DmmUtil DmmUtil;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询素材库列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MaterialLibrary materialLibrary)
    {
        Map<String,Object>  conditions = new HashMap<>();
        conditions.put("searchValue", materialLibrary.getKeyword());
        conditions.put("companyId",getLoginUser().getCompanyId());
        if (!materialLibrary.getCategoryId().equals("all")){
            conditions.put("categoryId", materialLibrary.getCategoryId());
        }
        startPage();
        List<MaterialLibrary> list = materialLibraryService.selectMaterialLibraryList(conditions);
        return getDataTable(list);
    }

    /**
     * 获取素材库详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid) throws Exception {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",companyId);
        MaterialLibrary info = materialLibraryService.selectMaterialLibraryById(conditions);
        if (info.getType().equals("document")){
            DmmUtil.createImmClient();
            Map<String,Object> respond = DmmUtil.getWebOfficeToken(info.getObject(),userInfo.getUserId());
            info.setTransCodeUri((String) respond.get("webofficeURL"));
            info.setAccessToken((String) respond.get("accessToken"));
            info.setRefreshToken((String) respond.get("refreshToken"));
        }
        return AjaxResult.success(info);
    }

    /**
     * 新增素材库
     */
    @Log(title = "素材库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialLibrary materialLibrary) throws Exception {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        String uuid = UUID.randomUUID().toString();
        materialLibrary.setUuid(uuid);
        materialLibrary.setCreateUserId(userInfo.getUserId());
        materialLibrary.setCreateUserName(userInfo.getUserName());
        materialLibrary.setUpdateUserId(userInfo.getUserId());
        materialLibrary.setUpdateUserName(userInfo.getUserName());
        materialLibrary.setCompanyId(userInfo.getCompanyId());
        materialLibraryService.insertMaterialLibrary(materialLibrary);
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",companyId);
        return AjaxResult.success(materialLibraryService.selectMaterialLibraryById(conditions));
    }

    /**
     * 修改素材库
     */
    @Log(title = "素材库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialLibrary materialLibrary)
    {
        return toAjax(materialLibraryService.updateMaterialLibrary(materialLibrary));
    }

    @PostMapping("/addMove")
    public AjaxResult addMove(@RequestBody MaterialLibrary materialLibrary) {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        String categoryId = materialLibrary.getCategoryId();
        Map<String,Object> where = new HashMap<>();
        where.put("uuid",categoryId);
        where.put("companyId",companyId);
        Category info = categoryService.selectCategoryById(where);
        String selectIds = materialLibrary.getSelectIds();
        if (selectIds != null){
            List<String> selectIdList = JSON.parseArray(selectIds, String.class);
            for (String uuid : selectIdList){
                MaterialLibrary dataFile = new MaterialLibrary();
                dataFile.setUuid(uuid);
                dataFile.setCategoryId(categoryId);
                dataFile.setCategoryTitle(info.getTitle());
                dataFile.setCompanyId(companyId);
                materialLibraryService.updateMaterialLibraryInfo(dataFile);
            }
        }
        return toAjax(1);
    }

    /**
     * 删除素材库
     */
    @Log(title = "素材库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuid}")
    public AjaxResult remove(@PathVariable("uuid") String uuid)
    {
        return toAjax(materialLibraryService.deleteMaterialLibraryById(uuid));
    }
}
