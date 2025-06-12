package com.edu.knowledge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 课程课件表对象 courseware
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Data
public class Courseware extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    /** 课件uuid */
    private String uuid;

    /** 课件标题 */
    private String title;

    /** 课件类型 */
    private String type;

    private String fileType;

    /** 课程ID */
    private String courseId;

    /** 权重 */
    private Integer weight;

    /** 0未删除1已删除 */
    private Integer isDelete;

    /** 课件时长 */
    private Integer length;

    private Integer shortTime = 0;

    private Integer progress = 0;

    private Boolean isUnlocked = false;

    private String sourceId;

    private String from = "course";

    private String lessonValue;

    @JsonIgnore
    private String createUserId;

    @JsonIgnore
    private String updateUserId;

    /** 公司ID */
    @JsonIgnore
    private String companyId;


    private Object nextCourseware;
}
