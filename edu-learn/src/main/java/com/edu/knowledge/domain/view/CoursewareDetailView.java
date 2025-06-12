package com.edu.knowledge.domain.view;

import lombok.Data;

@Data
public class CoursewareDetailView {

    /** 课件uuid */
    private String uuid;

    /** 课件标题 */
    private String title;

    /** 课件类型 */
    private String type;

    /** 课件时长 */
    private Integer length;

    private Integer shortTime = 0;

    private Integer progress = 0;

    private String learnStatus = "learning";

    private Boolean isUnlocked = false;

    private Object nextCourseware;
}
