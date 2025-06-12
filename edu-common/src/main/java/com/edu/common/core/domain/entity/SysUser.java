package com.edu.common.core.domain.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.edu.common.core.domain.BaseEntity;
import com.edu.common.xss.Xss;

/**
 * 用户对象 sys_user
 * 
 * @author edu
 */
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String userId;

    private String unionId;

    /** 部门ID */
    private String deptId;

    private String postNames;

    private String keyword;

    private String position;

    private String jobNumber;

    /** 用户账号 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String phonenumber;

    /** 用户性别 */
    private String sex;

    /** 用户头像 */
    private String avatar;

    private String newTime;

    private String updateTime;

    /** 密码 */
    private String password;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    private Integer status;

    private Integer statusType;

    /** 删除标志（0代表存在 2代表删除） */
    private Integer isDelete;

    private Boolean isNew = false;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private Date loginDate;

    /** 部门对象 */
    private SysDept dept;

    /** 角色对象 */
    private List<SysRole> roles;

    /** 角色组 */
    private String[] roleIds;

    /** 岗位组 */
    private String[] postIds;

    /** 权限 */
    @JsonIgnore
    private Set<String> scopeIds;

    /** 是否为管理员 */
    private Integer isMaster;

    private String deptIds;

    @JsonIgnore
    private Set<String> allDeptParent;

    /** 角色ID */
    private String roleId;

    private List<Integer> department;

    /** 公司ID */
    private String companyId;

    /** 公司name */
    private String companyName;

    private String deptNames;

    private String deptNamePath;

    /**
     * 权限列表
     */
    private Set<String> permissions;


    public SysUser()
    {

    }

    public SysUser(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPostNames()
    {
        return postNames;
    }

    public void setPostNames(String postNames)
    {
        this.postNames = postNames;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getJobNumber()
    {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber)
    {
        this.jobNumber = jobNumber;
    }

    public String getDeptNames()
    {
        return deptNames;
    }

    public void setDeptNames(String deptNames)
    {
        this.deptNames = deptNames;
    }

    public String getUnionId()
    {
        return unionId;
    }

    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getNewTime()
    {
        return newTime;
    }

    public void setNewTime(String newTime)
    {
        this.newTime = newTime;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    //是否管理员
    public static boolean isAdmin(String userId)
    {
        return userId != null;
    }

    public Boolean getIsNew()
    {
        return isNew;
    }

    //是否管理员
    public void setIsNew(Boolean isNew)
    {
        this.isNew  = isNew;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public Set<String> getAllDeptParent()
    {
        return allDeptParent;
    }

    public void setAllDeptParent(Set<String> allDeptParent)
    {
        this.allDeptParent = allDeptParent;
    }

    public List<Integer> getDepartment()
    {
        return department;
    }

    public void setDepartment(List<Integer> department)
    {
        this.department = department;
    }

    @JsonIgnore
    @JsonProperty
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatusType()
    {
        return statusType;
    }

    public void setStatusType(Integer statusType)
    {
        this.statusType = statusType;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public String[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Set<String> getScopeIds()
    {
        return scopeIds;
    }

    public void setScopeIds(Set<String> scopeIds)
    {
        this.scopeIds = scopeIds;
    }

    public String[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(String[] postIds)
    {
        this.postIds = postIds;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public void setIsMaster(Integer isMaster)
    {
        this.isMaster = isMaster;
    }

    public Integer getIsMaster()
    {
        return isMaster;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(String deptIds)
    {
        this.deptIds = deptIds;
    }

    public String getDeptNamePath()
    {
        return deptNamePath;
    }

    public void setDeptNamePath(String deptNamePath)
    {
        this.deptNamePath = deptNamePath;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("salt", getSalt())
            .append("status", getStatus())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
            .append("companyId", getCompanyId())
            .append("companyName", getCompanyName())
            .toString();
    }
}
