package com.edu.web.controller.system;

import java.util.List;

import com.edu.common.core.domain.entity.SysCompany;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.system.service.ICategoryService;
import com.edu.system.service.ISysCompanyService;
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
import com.edu.common.core.page.TableDataInfo;
import com.edu.common.enums.BusinessType;
import com.edu.system.domain.SysConfig;
import com.edu.system.service.ISysConfigService;

/**
 * 参数配置 信息操作处理
 * 
 * @author edu
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController
{
    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysCompanyService sysCompanyService;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 初始化系统数据
     */
    @GetMapping("/initSystemData")
    public AjaxResult initSystemData(SysConfig config)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        SysCompany companyInfo = sysCompanyService.selectSysCompanyById(companyId);
        if (companyInfo.getIsInit() == 0){
            //初始化资源分类
            categoryService.initializeCategory(companyId);
        }

        //初始化分类数据

        //初始化课程数据

        //初始化课件数据

        //初始化题库数据

        //初始化试卷数据

        //初始化考试数据

        //初始化问卷数据

        //初始化证书数据

        //初始化培训数据

        //初始化学习项目数据
        return AjaxResult.success("");
    }

    /**
     * 获取参数配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysConfig config)
    {
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    /**
     * 根据参数编号获取详细信息
     */
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable String configId)
    {
        return AjaxResult.success(configService.selectConfigById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public AjaxResult getConfigKey(@PathVariable String configKey)
    {
        return AjaxResult.success(configService.selectConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysConfig config)
    {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config)))
        {
            return AjaxResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(getUsername());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysConfig config)
    {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config)))
        {
            return AjaxResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy(getUsername());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable String[] configIds)
    {
        configService.deleteConfigByIds(configIds);
        return success();
    }

    /**
     * 刷新参数缓存
     */
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        configService.resetConfigCache();
        return AjaxResult.success();
    }
}
