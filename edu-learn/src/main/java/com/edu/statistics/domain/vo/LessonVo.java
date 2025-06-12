package com.edu.statistics.domain.vo;

import lombok.Data;

@Data
public class LessonVo {
    private String coursewareId;

    private String coursewareTitle;

    private String lessonType;

    private Integer display;

    private String materialId;

    private String learnStatus;

    private String progress;

    private String finishTime;
}
