package com.edu.system.domain;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 导师等级对象 mentor_grade
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
@Data
public class MentorGrade extends BaseEntity
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

    /** 创建人 */
    private String createUserId;

    /** 修改人id */
    private String updateUserId;

    /** 公司id */
    private String companyId;
}
