package com.edu.system.domain.vo;

import lombok.Data;

@Data
public class DeptVisibleVo {

    private String uuid;

    private String showName;

    private String type;

    private String parentId;

    private Integer userCount = 0;
}
