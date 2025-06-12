package com.edu.knowledge.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KnowledgeTypeCount {

    private Integer courseCount;

    private Integer growthCourseCount;

    private Integer totalLearners;

    private Integer totalCompleted;

    private BigDecimal totalDuration;

    private String finishedRatio = "0";
}
