package com.edu.knowledge.domain.vo;

import lombok.Data;

@Data
public class CoursewareJoinUser {
    /** 课件uuid */
    private String uuid;

    /** 课件标题 */
    private String title;

    /** 课件类型 */
    private String type;

    /** 课程ID */
    private String courseId;

    private Integer length;

    /** 权重 */
    private Integer weight;

    private Boolean isUnlocked = false;

    private Integer progress;
}
