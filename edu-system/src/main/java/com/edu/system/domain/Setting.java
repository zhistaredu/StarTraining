package com.edu.system.domain;

import com.edu.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 公司配置对象 setting
 * 
 * @author chuanzhiedu
 * @date 2024-04-19
 */
@Data
public class Setting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 同步状态 */
    private String sync;

    /** 公司id */
    private String companyId;
}
