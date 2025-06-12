package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.List;
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
import com.edu.system.domain.MentorGrade;
import com.edu.system.service.IMentorGradeService;
import com.edu.common.core.page.TableDataInfo;

/**
 * 导师等级Controller
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
@RestController
@RequestMapping("/system/mentorGrade")
public class MentorGradeController extends BaseController
{
    @Autowired
    private IMentorGradeService mentorGradeService;

    /**
     * 查询导师等级列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MentorGrade mentorGrade)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",userInfo.getCompanyId());
        startPage();
        List<MentorGrade> list = mentorGradeService.selectMentorGradeList(conditions);
        return getDataTable(list);
    }

    /**
     * 获取导师等级详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(mentorGradeService.selectMentorGradeById(conditions));
    }

    /**
     * 新增导师等级
     */
    @Log(title = "导师等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MentorGrade mentorGrade)
    {
        return toAjax(mentorGradeService.insertMentorGrade(mentorGrade));
    }

    /**
     * 修改导师等级
     */
    @Log(title = "导师等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MentorGrade mentorGrade)
    {
        return toAjax(mentorGradeService.updateMentorGrade(mentorGrade));
    }

    /**
     * 删除导师等级
     */
    @Log(title = "导师等级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mentorGradeService.deleteMentorGradeByIds(ids));
    }
}
