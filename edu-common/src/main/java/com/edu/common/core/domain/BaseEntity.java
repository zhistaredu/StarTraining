package com.edu.common.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Entity基类
 * 
 * @author edu
 */
@Data
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    @JsonIgnore
    private String searchValue;

    /** 创建者 */
    private String createBy;

    private List<String> timeScreen;

    private String relevancy = "yes";

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    private Map<String, Object> params;

    /** 请求参数 */
    private ArrayList<String> ids;

    /** 请求类型 */
    private String action;
}
