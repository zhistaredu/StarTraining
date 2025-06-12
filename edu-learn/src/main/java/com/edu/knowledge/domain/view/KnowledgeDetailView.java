package com.edu.knowledge.domain.view;

import lombok.Data;

@Data
public class KnowledgeDetailView {

    /** 唯一uuid */
    private String uuid;


    /** 课程标题 */
    private String title;

    private Integer credit;

    /** 分类ID */
    private String categoryId;

    /** 分类名称 */
    private String categoryTitle;

    /** 学习模式  liberty为自由式,unlock为解锁式 */
    private String learnMode;

    private String cdnPicture;

    private String lectorName;

    /** close倍速关闭 open开启 */
    private String speed;

    /** close关闭拖动open开启 */
    private String drag;

    private String certificateTitle;

    /** 发布时间 */
    private String publishTime;

    private String eligible;

    private Integer learnProgress;
}
