package com.edu.knowledge.domain.view;

import lombok.Data;

@Data
public class LessonView {

    private String uuid;

    private String courseId;

    private String stageId;

    private String sourceId;

    private String content;

    private Integer length = 0;

    private String title;

    private String type;

    private Integer number = 0;

    private String mediaUri;

    private Integer convertStatus = 1;

    private String transCodeUri;

    private String accessToken;

    private String refreshToken;

    private Float progressBar = 0.00F;

    private Integer progress = 0;

    private Object nextCourseware;
}
