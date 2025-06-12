package com.edu.framework.web.service;

import com.edu.common.core.domain.entity.SysCompany;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.StringUtils;
import com.edu.system.service.ISysCompanyService;
import com.edu.system.service.ISysUserRoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * token验证处理
 *
 * @author edu
 */
@Service
public class UserLoginService
{
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    // 令牌有效期（默认30分钟）

    protected static final Long MILLIS_SECOND = 1000L;

    protected static final Long MILLIS_MINUTE = 60 * 1000L;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysCompanyService sysCompanyService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public SysUser getUserToken(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims = parseToken(token);
            String userId = (String) claims.get("user_id");
            String companyId = (String) claims.get("company_id");
            SysCompany companyInfo = sysCompanyService.selectSysCompanyById(companyId);
            if (companyInfo == null){
                return null;
            }
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("userId",userId);
            conditions.put("companyId",companyId);
            Map<String,Object> scope = sysUserRoleService.selectUserRoleList(conditions);
            // 解析对应的权限以及用户信息
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysUser.setUserName("$userName="+ userId +"$");
            sysUser.setScopeIds((Set<String>) scope.get("scopeIds"));
            sysUser.setAllDeptParent((Set<String>) scope.get("allDeptParent"));//本部门及上级部门
            sysUser.setCompanyId(companyId);
            sysUser.setCompanyName(companyInfo.getCompanyName());
            Set<String> permission = permissionService.getMenuPermission(sysUser);
            sysUser.setPermissions(permission);
            return sysUser;
        }
        return null;
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public String createToken(Map<String, Object> claims)
    {
        Map<String, Object> jwtHeader = new HashMap<>();
        String token = Jwts.builder()
                .setHeader(jwtHeader)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        System.out.println("解析token验证");
        System.out.println(secret);
        System.out.println(token);
        System.out.println("解析结束");
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    public String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token)) {
            return token;
        } else {
            return null;
        }
    }
}
