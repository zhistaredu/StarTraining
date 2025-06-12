package com.edu.system.domain.vo;

import lombok.Data;

@Data
public class AssignVo {
    /**
     * 指派部门或人员id
     */
    private String uuid;

    /**
     * 指派部门或人员name
     */
    private String showName;

    private String type;

    /**
     * 指派部门或人员id
     */
    private String deptId;
}
