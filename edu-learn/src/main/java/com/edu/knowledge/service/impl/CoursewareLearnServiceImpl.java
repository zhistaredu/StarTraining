package com.edu.knowledge.service.impl;

import java.util.*;


import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.knowledge.domain.*;
import com.edu.knowledge.domain.vo.LessonProgress;
import com.edu.knowledge.service.*;
import com.edu.statistics.domain.vo.LessonLearnScreenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.knowledge.mapper.CoursewareLearnMapper;

/**
 * 课程课件学习Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Service
public class CoursewareLearnServiceImpl implements ICoursewareLearnService
{
    @Autowired
    private CoursewareLearnMapper coursewareLearnMapper;

    @Autowired
    private IKnowledgeUserService knowledgeUserService;

    /**
     * 查询课程课件学习
     * 
     * @param conditions 课程课件学习主键
     * @return 课程课件学习
     */
    @Override
    public CoursewareLearn selectCoursewareLearnById(Map<String,Object> conditions)
    {
        return coursewareLearnMapper.selectCoursewareLearnById(conditions);
    }

    @Override
    public CoursewareLearn selectLastLearnInfo(Map<String,Object> conditions)
    {
        return coursewareLearnMapper.selectLastLearnInfo(conditions);
    }

    @Override
    public void startLessonLearn(Map<String,Object> conditions)
    {
        coursewareLearnMapper.selectCoursewareLearnById(conditions);
    }

    @Override
    public void finishLessonLearn(CoursewareLearn coursewareLearn)
    {
        updateCoursewareLearn(coursewareLearn);
        knowledgeUserService.syncCourseUserData(coursewareLearn);
    }

    @Override
    public CoursewareLearn updateCoursewareLearnProgress(LessonProgress lessonProgress) {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String coursewareId = lessonProgress.getCoursewareId();
        String courseId = lessonProgress.getCourseId();
        String stageId  = lessonProgress.getStageId();
        Float progressBar = lessonProgress.getProgressBar();//上次观看时长
        //获取学习记录
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("userId",userId);
        conditions.put("coursewareId",coursewareId);
        conditions.put("courseId",courseId);
        conditions.put("stageId",stageId);
        conditions.put("companyId",companyId);
        CoursewareLearn learnInfo = selectLastLearnInfo(conditions);
        learnInfo.setLearnTime(progressBar);
        learnInfo.setProgressBar(progressBar);
        learnInfo.setUpdateTime(DateUtils.getTime());//更新时间
        //如果进度完成100再次学习就不更新进度
        if (learnInfo.getProgress() < 100){
            learnInfo.setProgress((int) Math.floor((progressBar / (double) learnInfo.getLength()) * 100));
            if (learnInfo.getProgressBar() >= (learnInfo.getLength() - 5)){
                learnInfo.setProgress(100);
                learnInfo.setFinishTime(DateUtils.getTime());
                learnInfo.setLearnTime(Float.valueOf(learnInfo.getLength()));
            }
        }
        updateCoursewareLearn(learnInfo);
        knowledgeUserService.syncCourseUserData(learnInfo);
        return learnInfo;
    }

    /**
     * 查询课程课件学习列表
     * 
     * @param conditions 课程课件学习
     * @return 课程课件学习
     */
    @Override
    public List<CoursewareLearn> selectCoursewareLearnList(Map<String,Object> conditions)
    {
        return coursewareLearnMapper.selectCoursewareLearnList(conditions);
    }

    @Override
    public LessonLearnScreenVo getLessonLearnScreenList(Map<String,Object> conditions)
    {
        return coursewareLearnMapper.getLessonLearnScreenList(conditions);
    }

    @Override
    public int insertCoursewareLearn(CoursewareLearn coursewareLearn)
    {
        return coursewareLearnMapper.insertCoursewareLearn(coursewareLearn);
    }

    /**
     * 新增课程课件学习
     * 
     * @param coursewareLearn 课程课件学习
     * @return 结果
     */
    @Override
    public int insertCoursewareLearnInfo(CoursewareLearn coursewareLearn)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String courseId = coursewareLearn.getCourseId();
        String sourceId = coursewareLearn.getSourceId();
        String coursewareId = coursewareLearn.getCoursewareId();
        String time = DateUtils.getTime();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("courseId",courseId);
        conditions.put("sourceId",sourceId);
        conditions.put("coursewareId",coursewareId);
        conditions.put("userId",userId);
        conditions.put("companyId",companyId);
        CoursewareLearn learnInfo = selectCoursewareLearnById(conditions);
        if (learnInfo == null){
            coursewareLearn.setUserId(userId);
            coursewareLearn.setUserName(userInfo.getUserName());
            coursewareLearn.setProgressBar((float) 0);
            coursewareLearn.setStartTime(time);
            coursewareLearn.setCreateTime(time);
            coursewareLearn.setUpdateTime(time);
            coursewareLearn.setCompanyId(companyId);
            coursewareLearnMapper.insertCoursewareLearn(coursewareLearn);
        } else {
            coursewareLearn.setUpdateTime(time);
            updateCoursewareLearn(coursewareLearn);
        }
        return 1;
    }

    /**
     * 修改课程课件学习
     * 
     * @param coursewareLearn 课程课件学习
     * @return 结果
     */
    @Override
    public int updateCoursewareLearn(CoursewareLearn coursewareLearn)
    {
        return coursewareLearnMapper.updateCoursewareLearn(coursewareLearn);
    }

    /**
     * 批量删除课程课件学习
     * 
     * @param ids 需要删除的课程课件学习主键
     * @return 结果
     */
    @Override
    public int deleteCoursewareLearnByIds(Long[] ids)
    {
        return coursewareLearnMapper.deleteCoursewareLearnByIds(ids);
    }

    /**
     * 删除课程课件学习信息
     * 
     * @param id 课程课件学习主键
     * @return 结果
     */
    @Override
    public int deleteCoursewareLearnById(Long id)
    {
        return coursewareLearnMapper.deleteCoursewareLearnById(id);
    }
}
