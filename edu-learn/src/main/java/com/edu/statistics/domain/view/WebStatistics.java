package com.edu.statistics.domain.view;

import lombok.Data;

@Data
public class WebStatistics {

    private Integer page = 1;

    private Integer limit = 10;

    private String deptId;

    private String startTime;

    private String endTime;

    private String startTimestamp;

    private String endTimestamp;

}
