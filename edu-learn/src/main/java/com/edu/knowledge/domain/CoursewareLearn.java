package com.edu.knowledge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 课程课件学习对象 courseware_user
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Data
public class CoursewareLearn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    private String eventId;

    private String source;

    private String sourceId;

    /** 课程ID */
    private String courseId;

    @JsonIgnore
    private String courseTitle;

    private String stageId;

    /** 课件ID */
    private String coursewareId;

    private Object nextCourseware;

    /** 课件标题 */
    private String coursewareTitle;

    private Boolean open;

    @JsonIgnore
    /** 用户ID */
    private String userId;

    @JsonIgnore
    /** 用户姓名 */
    private String userName;

    @JsonIgnore
    /** 开始学习时间 */
    private String startTime;

    @JsonIgnore
    /** 完成时间 */
    private String finishTime;

    /** 学习时长 */
    private Float learnTime = 0.00F;

    /** 上次观看时间 */
    private Float progressBar = 0.00F;

    /** 课件总时长 */
    private Integer length = 0;

    /** 单个课件进度 */
    private Integer progress = 0;

    @JsonIgnore
    private Integer isDelete;

    @JsonIgnore
    private String companyId;

    @JsonIgnore
    private String companyName;

}
