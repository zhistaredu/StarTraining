package com.edu.knowledge.domain.vo;

import lombok.Data;

@Data
public class TrainTypeCount {

    private Integer trainCount;

    private Integer growthTrainCount;

    private Integer shouldCount;

    private Integer learnCount;

    private Integer finishedCount;

    private Float learningRatio = (float) 0;

    private Float finishedRatio = (float) 0;
}
