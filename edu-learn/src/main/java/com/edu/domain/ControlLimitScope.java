package com.edu.domain;

import lombok.Data;

@Data
public class ControlLimitScope {

    /** 唯一ID */
    private String homeId;

    private String name;

    private String targetId;

    private String scopeType;

    private String companyId;

}
