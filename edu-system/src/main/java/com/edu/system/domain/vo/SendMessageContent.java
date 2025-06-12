package com.edu.system.domain.vo;

import lombok.Data;

@Data
public class SendMessageContent {

    private Long agentId;

    private String useridList;

    private String image;

    private String content;

    private String title;

    private String templateId;

    private String pcMessageUrl;

    private String messageUrl;

    private String params;
}
