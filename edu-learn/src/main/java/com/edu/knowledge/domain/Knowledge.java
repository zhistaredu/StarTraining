package com.edu.knowledge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 课程主表对象 course
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Data
public class Knowledge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    /** 唯一uuid */
    private String uuid;

    @JsonIgnore
    private String keyword;

    /** 课程标题 */
    private String title;

    /** 课程状态-1已删除0未发布1已发布2已关闭3已删除 */
    private Integer status;

    /** 1删除0未删除 */
    @JsonIgnore
    private Integer isDelete;

    private Integer credit;

    private String certificate;

    /** 分类ID */
    private String categoryId;

    private String syncLearn;

    private String learnStatus;

    /** 分类名称 */
    private String categoryTitle;

    /** 学习模式  liberty为自由式,unlock为解锁式 */
    private String learnMode;

    /** 讲师id */
    private String lectorId;

    private String lectorName;

    private String assignModel;

    private String model;

    private String eligible;

    private Integer learnProgress;

    /** 课程封面 */
    private String picture;

    private String imgUrl;

    private String cdnPicture;

    /** close倍速关闭 open开启 */
    private String speed;

    /** close关闭拖动open开启 */
    private String drag;

    /** 课件数量 */
    private Integer coursewareNum;

    /** 课程总时长 */
    private Integer duration;

    /** 学员数量 */
    private Integer studentNum;

    /** 必修人数 */
    private Integer requiredNum;

    /** 已完成指派数 */
    private Integer finishRequiredNum;

    /** 选修总人数 */
    private Integer electiveNum;

    /** 已完成选修数 */
    private Integer finishElectiveNum;

    /** 发布时间 */
    @JsonIgnore
    private String publishTime;

    @JsonIgnore
    private String createUserId;

    @JsonIgnore
    private String updateUserId;

    /** 公司ID */
    @JsonIgnore
    private String companyId;
}
