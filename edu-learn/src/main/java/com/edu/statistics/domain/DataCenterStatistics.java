package com.edu.statistics.domain;

import lombok.Data;

import java.util.List;

@Data
public class DataCenterStatistics {

    private Integer pubCourseNum;

    private Integer noCourseNum;

    private Integer pubExamNum;

    private Integer noExamNum;

    private List<String> dateArr;

    private List<Integer> loginList;

    private List<Integer> learnTimeList;
}
