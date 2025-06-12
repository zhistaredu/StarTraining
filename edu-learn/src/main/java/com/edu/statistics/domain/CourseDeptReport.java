package com.edu.statistics.domain;

import lombok.Data;

@Data
public class CourseDeptReport {

    private String deptName;

    private String deptNamePath;

    private Integer learnNum;

    private Integer learnTime;

    private String learnRate = "0";

    private Integer deptUserNum;

    private Integer finishedNum;

    private String finishRate = "0";
}
