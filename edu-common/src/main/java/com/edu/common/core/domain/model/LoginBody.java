package com.edu.common.core.domain.model;

import lombok.Data;

/**
 * 用户登录对象
 * 
 * @author edu
 */
@Data
public class LoginBody
{
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}
