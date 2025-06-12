package com.edu.knowledge.domain.view;

import lombok.Data;

@Data
public class CoursewareLearnDetailView {

    /** 课程ID */
    private String courseId;

    private Boolean playing = false; //开始暂停播放按钮 true或false

    /** 课件ID */
    private String coursewareId;

    private Object nextCourseware;

    /** 课件标题 */
    private String coursewareTitle;

    /** 用户ID */
    private String userId;

    /** 用户姓名 */
    private String userName;

    /** 学习状态'learning'学习中'finished'已完成 */
    private String learnStatus;

    /** 开始学习时间 */
    private String startTime;

    /** 完成时间 */
    private String finishTime;

    /** 学习时长 */
    private Integer learnTime = 0;

    /** 上次观看时间 */
    private Float currentLength = 0.00F;

    /** 观看时长 */
    private Integer watchTime = 0;

    /** 观看次数 */
    private Integer watchNum = 0;

    /** 课件总时长 */
    private Integer length = 0;

    /** 单个课件进度 */
    private Integer progress = 0;
}
