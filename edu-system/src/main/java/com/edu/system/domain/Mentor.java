package com.edu.system.domain;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 导师对象 mentor
 * 
 * @author chuanzhiedu
 * @date 2023-11-25
 */
@Data
public class Mentor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 讲师id */
    private String mentorId;

    private String mentorName;

    /** $column.columnComment */
    private String mentorAvatar;

    /** 讲师等级id */
    private String mentorGrade;

    /** interior内部external外部 */
    private String mentorType;

    /** 讲师简介 */
    private String synopsis;

    /** 0男1女 */
    private Integer sex;

    /** 状态 */
    private Integer status;

    /** 1是删除 */
    private Integer isDelete;

    /** 创建人 */
    private String createUserId;

    /** 修改人 */
    private String updateUserId;

    /** 公司id */
    private String companyId;
}
