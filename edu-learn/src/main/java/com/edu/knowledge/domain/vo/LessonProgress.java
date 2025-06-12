package com.edu.knowledge.domain.vo;

import lombok.Data;

@Data
public class LessonProgress {

    private String from;

    private String coursewareId;

    private String courseId;

    private String stageId;

    private String sourceId;

    private Integer learnTime;

    private Float progressBar;

    private Boolean playing;

    private String learnStatus = "learning";

}
