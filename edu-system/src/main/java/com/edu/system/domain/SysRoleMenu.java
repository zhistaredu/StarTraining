package com.edu.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 角色和菜单关联 sys_role_menu
 * 
 * @author edu
 */
@Data
public class SysRoleMenu
{
    /** 角色ID */
    private String roleId;
    
    /** 菜单ID */
    private String menuId;

    private String companyId;
}
