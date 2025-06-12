package com.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.edu.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class KnowledgeControlLimit extends BaseEntity {

    private String targetId;

    private String uuid;

    private String title;

    @JsonIgnore
    private String picture;

    private String imgUrl;

    private String learnMode;

    /** 讲师id */
    private String lectorId;

    private String lectorName;

    private String speed;

    private String drag;

    private Integer coursewareNum;

    private Integer totalLength;

    private Integer studentNum;

    private Integer requiredNum;

    private Integer electiveNum;

    private String categoryId;

    private String categoryTitle;

    private Integer progress;

    private String learnStatus;

    private String finishTime;

    /** -1删除,0未发布1发布2关闭 */
    private Integer status;

    /** 公司ID */
    private String companyId;

}
