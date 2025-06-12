package com.edu.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 讲师等级对象 lector_grade
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
@Data
public class LectorGrade extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 唯一id */
    private String uuid;

    /** 等级名称 */
    private String title;

    /** interior内部external外部 */
    private String type;

    /** 等级说明 */
    private String synopsis;

    @JsonIgnore
    private String createUserId;

    @JsonIgnore
    private String updateUserId;

    /** 公司id */
    private String companyId;
}
