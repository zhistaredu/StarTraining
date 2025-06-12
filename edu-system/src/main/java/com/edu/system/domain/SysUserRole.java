package com.edu.system.domain;

import lombok.Data;

import java.util.Map;

/**
 * 用户和角色关联 sys_user_role
 * 
 * @author edu
 */
@Data
public class SysUserRole
{
    /** 用户ID */
    private String userId;

    private String userName;

    private String[] userIds;
    
    /** 角色ID */
    private String roleId;

    private String roleType;

    private Long scopeType;

    private Integer isDelete;

    private String scopeIds;

    private String scopeNames;

    private String companyId;

    private String companyName;

    private Map<String,Object> scopes;
}
