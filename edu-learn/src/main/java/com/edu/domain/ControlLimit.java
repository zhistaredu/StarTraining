package com.edu.domain;

import java.math.BigDecimal;
import java.util.List;

import com.edu.system.domain.vo.AssignVo;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 数据可见范围对象 knowledge_visible
 * 
 * @author chuanzhiedu
 * @date 2023-01-23
 */
@Data
public class ControlLimit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 递增ID */
    private Long id;

    private String uuid;

    private String title;

    private String passStatus;

    private String startTime;

    private String endTime;

    private Integer attemptNum;

    private Integer duration;

    private Integer attempt;

    private BigDecimal userPassScore = BigDecimal.valueOf(0);

    private BigDecimal totalScore = BigDecimal.valueOf(0);

    private BigDecimal passScore = BigDecimal.valueOf(0);

    private String passTime;

    private String userStatus;

    private String userStatusStr;

    private String readType;

    /** 唯一ID */
    private String homeId;

    private String name;

    private String scopeType;

    private String values;

    private String module;

    private String model;

    private String data;

    /** 目标Id */
    private String targetId;

    /** 部门列表 */
    private List<AssignVo> deptList;

    /** 人员列表 */
    private List<AssignVo> userList;

    /** 目标标题 */
    private String targetTitle;

    /** 课程类型微课、文档等 */
    private String targetType;

    /** 课程分类ID */
    private String categoryId;

    /** 分类名称 */
    private String categoryTitle;

    /** -1删除,0未发布1发布2关闭 */
    private Integer status;

    /** 是否删除 */
    private Integer isDelete;

    /** 创建者用户ID */
    private String createUserId;

    /** 更新者用户ID */
    private String updateUserId;

    /** 公司ID */
    private String companyId;

    /** 公司名称 */
    private String companyName;
}
