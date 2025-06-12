package com.edu.common.core.domain.entity;

import com.edu.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Category  extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 递增ID */
    private Long id;

    /** 唯一ID */
    private String uuid;

    /** 分类标题 */
    private String title;

    /** 父ID */
    private String parentId;

    private String categoryPath;

    /** 状态 */
    private Integer status;

    /** 是否删除 */
    private Integer isDelete;

    /** 权重 */
    private Long weight;

    /** 创建者用户ID */
    private String createUserId;

    /** 更新者用户ID */
    private String updateUserId;

    /** 公司ID */
    private String companyId;

    /** 公司名称 */
    private String companyName;

    private List<Category> children = new ArrayList<>();
}
