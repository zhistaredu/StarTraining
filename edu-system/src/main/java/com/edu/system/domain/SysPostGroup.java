package com.edu.system.domain;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 岗位组对象 sys_post_group
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@Data
public class SysPostGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 递增ID */
    private Long id;

    /** 唯一ID */
    private String uuid;

    /** 分类标题 */
    private String title;

    /** 是否删除 */
    private Integer isDelete;

    /** 创建者用户ID */
    private String createUserId;

    /** 更新者用户ID */
    private String updateUserId;

    /** 公司ID */
    private String companyId;

    /** 公司名称 */
    private String companyName;
}
