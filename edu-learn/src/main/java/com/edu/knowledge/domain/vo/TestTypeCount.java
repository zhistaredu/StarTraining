package com.edu.knowledge.domain.vo;

import lombok.Data;

@Data
public class TestTypeCount {

    private Integer testCount;

    private Integer growthTestCount;

    private Integer shouldCount;

    private Integer joinCount;

    private Integer passedCount;

    private String passedRatio = "0";

}
