package com.edu.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.annotation.Log;
import com.edu.common.constant.UserConstants;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.core.domain.entity.SysDictType;
import com.edu.common.core.page.TableDataInfo;
import com.edu.common.enums.BusinessType;
import com.edu.common.utils.poi.ExcelUtil;
import com.edu.system.service.ISysDictTypeService;

/**
 * 数据字典信息
 * 
 * @author edu
 */
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController
{
    @Autowired
    private ISysDictTypeService dictTypeService;

    @GetMapping("/list")
    public TableDataInfo list(SysDictType dictType)
    {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictType dictType)
    {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        util.exportExcel(response, list, "字典类型");
    }

    /**
     * 查询字典类型详细
     */
    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable String dictId)
    {
        return AjaxResult.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return AjaxResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(getUsername());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return AjaxResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(getUsername());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public AjaxResult remove(@PathVariable String[] dictIds)
    {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return success();
    }

    /**
     * 刷新字典缓存
     */
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        dictTypeService.resetDictCache();
        return AjaxResult.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return AjaxResult.success(dictTypes);
    }
}
