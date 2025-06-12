package com.edu.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 讲师管理对象 lector
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
@Data
public class Lector extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 讲师id */
    private String lectorId;

    /** 讲师名称 */
    private String lectorName;

    /** $column.columnComment */
    private String lectorAvatar;

    /** 讲师等级id */
    private String lectorGrade;

    private String lectorGradeName;

    /** interior内部external外部 */
    private String lectorType;

    private String contactWay;

    /** 讲师简介 */
    private String synopsis;

    /** man男woman女 */
    private String sex;

    private Integer courseCount;

    private Integer trainCount;

    /** 状态 */
    private Integer status;

    /** 1是删除 */
    private Integer isDelete;

    @JsonIgnore
    private String createUserId;

    @JsonIgnore
    private String updateUserId;

    /** 公司id */
    private String companyId;

    private Integer courseNum = 0;

    private Integer trainNum = 0;
}
