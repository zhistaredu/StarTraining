package com.edu.statistics.domain;

import com.edu.statistics.domain.vo.LessonVo;
import lombok.Data;

import java.util.Map;

@Data
public class CourseUserReport {

    private String userId;

    private String userName;

    private String deptNames;

    private String targetId;

    private String targetTitle;

    private String position;

    private String jobNumber;

    private String progress;

    private String learnStatus;

    private String finishTime;

    private String createTime;

    private Integer learnTime;

    private String coursewareId;

    private String materialId;

    private String lessonType;

    private String coursewareTitle;

    private Integer display;

    private String lessonProgress;

    private String lessonFinishTime;

    private String lessonStatus;

    private Integer isDelete;

    private Map<String,LessonVo> coursewareList;
}
