package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.Category;
import com.edu.common.core.domain.entity.SysUser;
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
import com.edu.system.service.ICategoryService;

/**
 * 资源分类Controller
 * 
 * @author chuanzhiedu
 * @date 2023-08-22
 */
@RestController
@RequestMapping("/system/category")
public class CategoryController extends BaseController
{
    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询资源分类列表
     */
    @GetMapping("/list")
    public AjaxResult list(Category category)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("isDelete",0);
        conditions.put("parentId",category.getParentId());
        conditions.put("companyId",getLoginUser().getCompanyId());
        List<Category> list = categoryService.selectCategoryList(conditions);
        int count = categoryService.selectCategoryCount(conditions);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data", categoryService.buildCategoryTreeSelect(list));
        ajax.put("count", count);
        ajax.put("status", "ok");
        ajax.put("message","请求成功");
        return ajax;
    }

    /**
     * 获取资源分类详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> where = new HashMap<>();
        where.put("uuid",uuid);
        where.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(categoryService.selectCategoryById(where));
    }

    /**
     * 新增资源分类
     */
    @Log(title = "资源分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Category category)
    {
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改资源分类
     */
    @Log(title = "资源分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Category category)
    {
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除资源分类
     */
    @Log(title = "资源分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuid}")
    public AjaxResult remove(@PathVariable("uuid") String uuid)
    {
        return toAjax(categoryService.deleteCategoryById(uuid));
    }
}
