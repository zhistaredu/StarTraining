package com.edu.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.annotation.Log;
import com.edu.common.constant.UserConstants;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.enums.BusinessType;
import com.edu.common.utils.SecurityUtils;
import com.edu.common.utils.StringUtils;
import com.edu.system.service.ISysUserService;

/**
 * 个人信息 业务处理
 * 
 * @author edu
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile()
    {
        SysUser user = getLoginUser();
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(user.getUserName()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user)
    {
        SysUser sysUser = getLoginUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        if (userService.updateUserProfile(user) > 0)
        {
            // 更新缓存用户信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPhonenumber(user.getPhonenumber());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword)
    {
        SysUser loginUser = getLoginUser();
        String userName = loginUser.getUserName();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0)
        {
            // 更新缓存用户密码
            loginUser.setPassword(SecurityUtils.encryptPassword(newPassword));
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

}
