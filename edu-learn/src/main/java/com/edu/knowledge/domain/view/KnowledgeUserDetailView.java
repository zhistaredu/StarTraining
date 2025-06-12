package com.edu.knowledge.domain.view;

import lombok.Data;

@Data
public class KnowledgeUserDetailView {
    private String targetId;

    private String targetTitle;

    private String userId;

    private String userName;

    private Integer coursewareNum;

    private Integer coursewareFinishNum;

    private Integer progress = 0;

    private Integer credit;

    private String learnStatus;

    private String finishTime;

    private Object lastLearnData;
}
