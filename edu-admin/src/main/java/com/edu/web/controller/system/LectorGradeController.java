package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.Map;

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
import com.edu.system.domain.LectorGrade;
import com.edu.system.service.ILectorGradeService;

/**
 * 讲师等级Controller
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/lector/lectorGrade")
public class LectorGradeController extends BaseController
{
    @Autowired
    private ILectorGradeService lectorGradeService;

    /**
     * 查询讲师等级列表
     */
    @GetMapping("/list")
    public AjaxResult list(LectorGrade lectorGrade)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(lectorGradeService.selectLectorGradeList(conditions));
    }

    /**
     * 获取讲师等级详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(lectorGradeService.selectLectorGradeById(conditions));
    }

    /**
     * 新增讲师等级
     */
    @Log(title = "讲师等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LectorGrade lectorGrade)
    {
        return toAjax(lectorGradeService.insertLectorGrade(lectorGrade));
    }

    /**
     * 修改讲师等级
     */
    @Log(title = "讲师等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LectorGrade lectorGrade)
    {
        return toAjax(lectorGradeService.updateLectorGrade(lectorGrade));
    }

    /**
     * 删除讲师等级
     */
    @Log(title = "讲师等级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(lectorGradeService.deleteLectorGradeByIds(ids));
    }

    /**
     * 查询讲师等级列表
     */
    @GetMapping("/home/list")
    public AjaxResult homeList(LectorGrade lectorGrade)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(lectorGradeService.selectLectorGradeList(conditions));
    }
}
