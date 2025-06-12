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
import com.edu.system.domain.Mentor;
import com.edu.system.service.IMentorService;
import com.edu.common.core.page.TableDataInfo;

/**
 * 导师Controller
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
@RestController
@RequestMapping("/system/mentor")
public class MentorController extends BaseController
{
    @Autowired
    private IMentorService mentorService;

    /**
     * 查询导师列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Mentor mentor)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",userInfo.getCompanyId());
        startPage();
        List<Mentor> list = mentorService.selectMentorList(conditions);
        return getDataTable(list);
    }

    /**
     * 获取导师详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(mentorService.selectMentorById(conditions));
    }

    /**
     * 新增导师
     */
    @Log(title = "导师", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Mentor mentor)
    {
        return toAjax(mentorService.insertMentor(mentor));
    }

    /**
     * 修改导师
     */
    @Log(title = "导师", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Mentor mentor)
    {
        return toAjax(mentorService.updateMentor(mentor));
    }

    /**
     * 删除导师
     */
    @Log(title = "导师", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mentorService.deleteMentorByIds(ids));
    }
}
