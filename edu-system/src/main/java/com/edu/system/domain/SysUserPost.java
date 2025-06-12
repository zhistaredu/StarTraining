package com.edu.system.domain;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 用户与岗位关联对象 sys_user_post
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@Data
public class SysUserPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户ID */
    private String userId;

    private String userName;

    /** 岗位ID */
    private String postId;

    /** part兼职 */
    private String type;

    /** 1删除 */
    private Integer isDelete;

    /** 公司id */
    private String companyId;

    /** 公司名称 */
    private String companyName;

    private String data;
}
