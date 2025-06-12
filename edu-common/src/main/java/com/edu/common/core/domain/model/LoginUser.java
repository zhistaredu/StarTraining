package com.edu.common.core.domain.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.edu.common.core.domain.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.alibaba.fastjson2.annotation.JSONField;

/**
 * 登录用户身份权限
 * 
 * @author edu
 */
public class LoginUser implements UserDetails
{
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 管理权限id
     */
    private List<String> scopeIds;

    /**
     * 管理权限名称
     */
    private List<String> scopeNames;

    /**
     * 管理权限组
     */
    private Map<String,String> scopes;

    /**
     * 用户信息
     */
    private SysUser user;

    public String getUserId()
{
    return userId;
}

    public void setUserId(String userId)
    {
        this.userId = userId;
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

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public List<String> getScopeIds()
    {
        return scopeIds;
    }

    public void setScopeIds(List<String> scopeIds)
    {
        this.scopeIds = scopeIds;
    }

    public List<String> getScopeNames()
    {
        return scopeNames;
    }

    public void setScopeNames(List<String> scopeNames)
    {
        this.scopeNames = scopeNames;
    }

    public Map<String,String> getScopes()
    {
        return scopes;
    }

    public void setScopes(Map<String,String> scopes)
    {
        this.scopes = scopes;
    }

    public LoginUser()
    {
    }

    public LoginUser(SysUser user, Set<String> permissions)
    {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(String userId, String deptId,String companyId, SysUser user, Set<String> permissions)
    {
        this.userId = userId;
        this.deptId = deptId;
        this.user = user;
        this.permissions = permissions;
        this.companyId = companyId;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     * 
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     * 
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     * 
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public Long getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Long loginTime)
    {
        this.loginTime = loginTime;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation()
    {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation)
    {
        this.loginLocation = loginLocation;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public Long getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Long expireTime)
    {
        this.expireTime = expireTime;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    public SysUser getUser()
    {
        return user;
    }

    public void setUser(SysUser user)
    {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

}
