package com.edu.knowledge.domain.vo;

import lombok.Data;

@Data
public class LessonData {

    private String uuid;

    private String title;

    private String type;

    private Integer length = 0;
}
