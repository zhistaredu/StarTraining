package com.edu.common.core.domain.entity;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 公司对象 sys_company
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
@Data
public class SysCompany extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 公司id */
    private String companyId;

    /** 公司名称 */
    private String companyName;

    private String corpLogo;

    private String type;

    /** 永久授权码 */
    private String permanentCode;

    /** 显示顺序 */
    private Integer orderNum;

    /** 是否锁住 */
    private Integer isLock;

    private String agentId;

    private Integer authMode;

    /** 是否开启系统权限 */
    private Integer isSystemAuthOpen;

    /** 负责人 */
    private String leader;

    private String authUserId;

    private Integer usedSpace;

    private Integer extraSpace;

    private Integer totalSpace;

    private Double totalSpaceGb;

    private Double usedSpaceGb;

    private Double freeSpaceGb;

    private Integer days;

    private Integer transCodeLength;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    private String status;

    private Integer isDelete;

    private Integer isPay;

    private Integer isInit;

    private String periodValidity;

    private String edition;

    private Integer buyNum;

    private Integer accreditNum;

}
