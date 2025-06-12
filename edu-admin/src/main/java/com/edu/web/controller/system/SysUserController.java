package com.edu.web.controller.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.edu.common.core.domain.entity.SysCompany;
import com.edu.knowledge.service.ICreditLogService;
import com.edu.framework.web.service.SysPermissionService;
import com.edu.service.IControlLimitService;
import com.edu.system.domain.SysUserRole;
import com.edu.system.domain.vo.DeptUserVisibleVo;
import com.edu.system.service.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.edu.common.annotation.Log;
import com.edu.common.constant.UserConstants;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.core.domain.entity.SysRole;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.core.page.TableDataInfo;
import com.edu.common.enums.BusinessType;
import com.edu.common.utils.SecurityUtils;
import com.edu.common.utils.StringUtils;
import com.edu.common.utils.poi.ExcelUtil;

/**
 * 用户信息
 *
 * @author edu
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ICreditLogService creditLogService;

    @Autowired
    private ISysCompanyService sysCompanyService;

    @Autowired
    private IControlLimitService controlLimitService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("deptId",user.getDeptId());
        conditions.put("isDelete",0);
        conditions.put("companyId",userInfo.getCompanyId());
        startPage();
        List<SysUser> list = userService.selectUserDeptPathList(conditions);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        for (SysUser items : list){
            if (!items.getNewTime().equals("1970-01-01 00:00:00") && time.compareTo(items.getNewTime()) > 0) {
                items.setIsNew(true);
            }
        }
        return getDataTable(list);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getUserInfo")
    public AjaxResult getUserInfo()
    {
        SysUser user = getLoginUser();
        // 角色集合和权限集合
        Map<String, Set<String>> rolesAndPerms = permissionService.getRolePermission(user);
        //角色和菜单都不为空则是后台管理员
        Map<String,Object> master = new HashMap<>();
        master.put("is_master",0);
        if (!rolesAndPerms.get("roles").isEmpty() || !rolesAndPerms.get("perms").isEmpty()){
            master.put("is_master",1);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", rolesAndPerms.get("roles"));
        ajax.put("permissions", rolesAndPerms.get("perms"));
        ajax.put("status","ok");
        ajax.put("data",master);
        return ajax;
    }

    /**
     * 获取用户列表
     */
    @GetMapping("/getDeptUserList")
    public AjaxResult getDeptUserList(SysUser user)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        if (StringUtils.isEmpty(user.getDeptId())){
            List<DeptUserVisibleVo> viewData = new ArrayList<>();
            return AjaxResult.success(viewData);
        }
        conditions.put("deptId",user.getDeptId());
        conditions.put("isDelete",0);
        conditions.put("companyId",userInfo.getCompanyId());
        List<DeptUserVisibleVo> list = userService.selectDeptUserList(conditions);
        return AjaxResult.success(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Map<String,Object> conditions)
    {
        List<SysUser> list = userService.selectUserList(conditions);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) String userId)
    {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", roles);
        if (StringUtils.isNotNull(userId))
        {
            Map<String,Object> conditions = new HashMap<>();
            conditions.put("userId",userId);
            conditions.put("companyId",getLoginUser().getCompanyId());
            SysUser sysUser = userService.selectUserById(conditions);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            List<SysUserRole> userRoles = userRoleService.selectUserRoleByList(conditions);
            ajax.put("roleIds", userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUser user)
    {
        return toAjax(userService.updateUser(user));
    }


    /**
     * 删除用户
     */
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        if (ArrayUtils.contains(userIds, getUserId()))
        {
            return error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user)
    {
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        user.setCompanyId(getLoginUser().getCompanyId());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") String userId)
    {
        AjaxResult ajax = AjaxResult.success();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("userId",userId);
        conditions.put("companyId",getLoginUser().getCompanyId());
        SysUser user = userService.selectUserById(conditions);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(String userId, String[] roleIds)
    {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    @GetMapping("/mineData")
    public AjaxResult mineData() throws ParseException {
        SysUser userInfo = getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        SysCompany companyInfo = sysCompanyService.selectSysCompanyById(companyId);

        String periodValidity = companyInfo.getPeriodValidity();
        String period = periodValidity.substring(0, 10);
        // 创建两个日期对象
        Date date1 = new Date();
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(periodValidity);
        // 计算两个日期之间的天数差
        long diff = date2.getTime() - date1.getTime();
        int days = (int) (diff / (1000 * 60 * 60 * 24));

        Map<String,Object> userWhere = new HashMap<>();
        userWhere.put("userId",userId);
        userWhere.put("companyId",companyId);
        SysUser userData = userService.selectUserById(userWhere);
        Map<String,Object> user = new HashMap<>();
        user.put("userId",userId);
        user.put("userName",userInfo.getUserName());
        user.put("avatar",userData.getAvatar());
        user.put("periodValidity",period);
        user.put("freeDays",days);
        user.put("edition",companyInfo.getEdition());
        user.put("companyId",companyId);
        user.put("companyName",userInfo.getCompanyName());
        Map<String,Object> where = new HashMap<>();
        where.put("scopeIds",userInfo.getAllDeptParent());
        where.put("companyId",companyId);
        where.put("table","knowledge_visible");
        int requiredNum = controlLimitService.selectVisibleHomeCount(where);
        int electiveNum = controlLimitService.selectVisibleHomeCount(where);
        Map<String,Object> awardWhere = new HashMap<>();
        awardWhere.put("userId",userId);
        awardWhere.put("companyId",companyId);
        int creditNum = creditLogService.selectCreditLogSum(awardWhere);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("reqNum",requiredNum);
        ajax.put("optNum",electiveNum);
        ajax.put("creditNum",creditNum);
        ajax.put("userInfo",user);
        return ajax;
    }

    @GetMapping("/getCompanyData")
    public AjaxResult getCompanyData() throws ParseException {

        SysUser userInfo = getLoginUser();
        SysCompany companyInfo = sysCompanyService.selectSysCompanyById(userInfo.getCompanyId());

        double totalSpaceGb = companyInfo.getTotalSpace() / 1024;
        double usedSpaceGb = companyInfo.getUsedSpace() /1024;
        companyInfo.setTotalSpaceGb(Double.valueOf(String.format("%.2f", totalSpaceGb)));
        companyInfo.setUsedSpaceGb(Double.valueOf(String.format("%.2f", usedSpaceGb)));
        companyInfo.setFreeSpaceGb(Double.valueOf(String.valueOf(totalSpaceGb - usedSpaceGb)));

        String periodValidity = companyInfo.getPeriodValidity();
        String period = periodValidity.substring(0, 10);
        companyInfo.setPeriodValidity(period);

        // 创建两个日期对象
        Date date1 = new Date();
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(periodValidity);

        // 计算两个日期之间的天数差
        long diff = date2.getTime() - date1.getTime();
        int days = (int) (diff / (1000 * 60 * 60 * 24));
        companyInfo.setDays(days);
        return AjaxResult.success(companyInfo);
    }

}
