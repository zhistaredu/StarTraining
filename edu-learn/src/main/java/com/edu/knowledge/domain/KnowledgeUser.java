package com.edu.knowledge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

import java.util.Set;

/**
 * 课程学员表对象 knowledge_user
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Data
public class KnowledgeUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    /** 目标id */
    private String targetId;

    /** 目标标题 */
    private String targetTitle;

    /** 用户ID */
    private String userId;

    /** 创建者用户姓名 */
    private String userName;

    /** 是否删除 */
    private Integer isDelete;

    /** 课件总数 */
    private Integer coursewareNum;

    /** 课件完成数量 */
    private Integer coursewareFinishNum;

    /** 学习总进度 */
    private Integer progress = 0;

    private Integer credit;

    /** 学习状态 */
    private String learnStatus;

    private Integer learnTime;

    /** 完成时间 */
    private String finishTime;

    /** 课程发布时间 */
    private String publishTime;

    private String source;

    private String sourceId;

    /** 公司ID */
    @JsonIgnore
    private String companyId;

    private Set<String> deptIds;

    private Object lastLearnData;
}
