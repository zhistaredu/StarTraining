package com.edu.common.core.domain.entity;

import java.util.Date;

import com.edu.common.annotation.Excel;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 用户对象 sys_user
 *
 * @author edu
 */
@Data
public class SysUserPath extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long id;

    /** 人员id */
    private String userId;

    private String unionId;

    /** 部门id */
    private String deptIds;

    private String deptNames;

    /** 用户账号 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    private String position;

    private String jobNumber;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String phonenumber;

    private String newTime;

    /** 用户性别（0男 1女 2未知） */
    private String sex;

    /** 头像地址 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 帐号状态（0正常 1停用） */
    private Integer status;

    private Integer statusType;

    /** 删除标志（0代表存在 2代表删除） */
    private Integer isDelete;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private Date loginDate;

    /** 创建者 */
    private String createUserId;

    /** 创建人名称 */
    private String createUserName;

    /** 更新者 */
    private String updateUserId;

    /** 修改人名称 */
    private String updateUserName;

    /** 公司id */
    @Excel(name = "公司id")
    private String companyId;

    /** 公司名称 */
    private String companyName;
}
