package com.edu.knowledge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 用户学分记录对象 credit_log
 * 
 * @author chuanzhiedu
 * @date 2023-11-13
 */
@Data
public class CreditLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 唯一id */
    private String uuid;

    /** 对象id */
    private String targetId;

    /** 对象名称 */
    private String targetTitle;

    /** 所属模块 */
    private String module;

    /** 获得学分数 */
    private Integer credit;

    /** 获得积分的用户id */
    private String userId;

    /** 获得积分的用户名 */
    private String userName;

    @JsonIgnore
    private Integer isDelete;

    /** 企业id */
    private String companyId;
}
