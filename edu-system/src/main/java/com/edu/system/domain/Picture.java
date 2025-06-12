package com.edu.system.domain;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 图片管理对象 picture
 * 
 * @author chuanzhiedu
 * @date 2023-10-27
 */
@Data
public class Picture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 递增主键ID */
    private Long id;

    /** 唯一ID */
    private String uuid;

    private String type;

    private String styleType;

    /** 源文件 */
    private String object;

    private String cdnObject;

    /** 0待上传1已上传 */
    private Integer status;

    /** 删除标记 */
    private Integer isDelete;

    /** 创建者用户ID */
    private String createUserId;

    /** 创建者姓名 */
    private String createUserName;

    /** 更新者用户Id */
    private String updateUserId;

    /** 更新者用户姓名 */
    private String updateUserName;

    /** 公司ID */
    private String companyId;

    /** 公司名称 */
    private String companyName;
}
