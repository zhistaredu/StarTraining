package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.knowledge.domain.CreditLog;
import com.edu.knowledge.domain.vo.CreditRanking;
import com.edu.knowledge.service.ICreditLogService;
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
 * 用户学分记录Controller
 * 
 * @author chuanzhiedu
 * @date 2023-11-13
 */
@RestController
@RequestMapping("/system/creditLog")
public class CreditLogController extends BaseController
{
    @Autowired
    private ICreditLogService creditLogService;

    /**
     * 查询用户学分记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CreditLog creditLog)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("userId",userInfo.getUserId());
        conditions.put("companyId",companyId);
        startPage();
        List<CreditLog> list = creditLogService.selectCreditLogList(conditions);
        return getDataTable(list);
    }

    /**
     * 新增用户学分记录
     */
    @Log(title = "用户学分记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CreditLog creditLog)
    {
        return toAjax(creditLogService.insertCreditLog(creditLog));
    }

    /**
     * 修改用户学分记录
     */
    @Log(title = "用户学分记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CreditLog creditLog)
    {
        return toAjax(creditLogService.updateCreditLog(creditLog));
    }

    /**
     * 删除用户学分记录
     */
    @Log(title = "用户学分记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(creditLogService.deleteCreditLogByIds(ids));
    }


    /**
     * 学员学分记录
     */
    @GetMapping("/home/list")
    public TableDataInfo homeList(CreditLog creditLog)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("userId",userInfo.getUserId());
        conditions.put("companyId",companyId);
        startPage();
        List<CreditLog> list = creditLogService.selectCreditLogList(conditions);
        return getDataTable(list);
    }

    /**
     * 学员学分汇总
     */
    @GetMapping("/home/info")
    public AjaxResult homeInfo(CreditLog creditLog)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("userId",userInfo.getUserId());
        conditions.put("companyId",companyId);
        int totalCredit = creditLogService.selectCreditLogSum(conditions);
        conditions.put("creditsToday","");
        int creditToday = creditLogService.selectCreditLogSum(conditions);
        Map<String,Object> result = new HashMap<>();
        result.put("totalCredit",totalCredit);
        result.put("creditToday",creditToday);
        result.put("ranking",1);
        return AjaxResult.success(result);
    }

    /**
     * 获取学分排行列表
     */
    @GetMapping("/home/creditList")
    public TableDataInfo creditList()
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",companyId);
        startPage();
        List<CreditRanking> list = creditLogService.selectCreditRanking(conditions);
        return getDataTable(list);
    }
}
