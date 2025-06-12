package com.edu.knowledge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 素材库对象 material_library
 * 
 * @author chuanzhiedu
 * @date 2023-10-14
 */
@Data
public class MaterialLibrary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String keyword;

    @JsonIgnore
    private Long id;

    /** 唯一ID */
    private String uuid;

    private String title;

    /** 0待上传1已上传 */
    private Integer status;

    /** 源文件 */
    private String object;

    /** 分类ID */
    private String categoryId;

    /** 分类名称 */
    private String categoryTitle;

    private String lectorId;

    private String useRange;

    private String lectorName;

    /** 删除标记 */
    private Integer isDelete;

    /** 文件名 */
    private String name;

    /** 文件大小字节 */
    private Long size;

    /** 长度（音视频则为时长，PPT/文档为页数） */
    private Integer length;

    @JsonIgnore
    private String transCode;

    /** 0转码中1转码成功2转码失败 */
    private Integer transCodeStatus;

    /** 媒体类型video视频audio音频document文档 */
    private String type;

    private String fileType;

    /** 转码任务ID */
    private String jobId;

    @JsonIgnore
    private String createUserId;

    @JsonIgnore
    private String createUserName;

    @JsonIgnore
    private String updateUserId;

    @JsonIgnore
    private String updateUserName;

    /** 公司ID */
    @JsonIgnore
    private String companyId;

    private String transCodeUri;

    private String accessToken;

    private String refreshToken;

    private String selectIds;
}
