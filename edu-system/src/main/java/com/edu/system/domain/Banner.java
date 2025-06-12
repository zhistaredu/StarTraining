package com.edu.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.edu.system.domain.vo.BannerVo;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

import java.util.List;

/**
 * banner图对象 banner
 * 
 * @author chuanzhiedu
 * @date 2023-10-20
 */
@Data
public class Banner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String object;

    private List<BannerVo> objectGroup;

    /** 0待上传1已上传 */
    private Integer status;

    @JsonIgnore
    private Integer isDelete;

    @JsonIgnore
    private String createUserId;

    @JsonIgnore
    private String createUserName;

    @JsonIgnore
    private String updateUserId;

    @JsonIgnore
    private String updateUserName;

    /** 公司ID */
    private String companyId;

    @JsonIgnore
    private String companyName;
}
