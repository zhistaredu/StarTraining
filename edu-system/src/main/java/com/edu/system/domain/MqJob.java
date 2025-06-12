package com.edu.system.domain;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 执行任务对象 mq_job
 * 
 * @author chuanzhiedu
 * @date 2023-01-24
 */
@Data
public class MqJob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 递增ID */
    private Long id;

    /** 唯一ID */
    private String uuid;

    /** 任务的针对的模块名称 */
    private String module;

    /** 任务的针对的动作 */
    private String action;

    private Integer actionNum;

    /** 目标ID */
    private String targetId;

    /** 任务针对的资源名称 */
    private String targetTitle;

    private String argument;

    /** -1删除0待执行1执行成功2执行失败3多次重试失败 */
    private Integer status;

    /** 创建者用户ID */
    private String createUserId;

    /** 更新者用户ID */
    private String updateUserId;

    /** 公司ID */
    private String companyId;
}
