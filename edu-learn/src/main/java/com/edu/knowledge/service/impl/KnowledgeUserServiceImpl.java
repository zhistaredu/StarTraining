package com.edu.knowledge.service.impl;

import java.util.*;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.knowledge.domain.*;
import com.edu.knowledge.mapper.CreditLogMapper;
import com.edu.knowledge.service.IKnowledgeService;
import com.edu.statistics.domain.CourseUserReport;
import com.edu.statistics.domain.CourseDeptReport;
import com.edu.statistics.mapper.CourseStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.knowledge.mapper.KnowledgeUserMapper;
import com.edu.knowledge.service.IKnowledgeUserService;

/**
 * 课程学员表Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Service
public class KnowledgeUserServiceImpl implements IKnowledgeUserService
{
    @Autowired
    private KnowledgeUserMapper courseUserMapper;

    @Autowired
    private IKnowledgeService courseService;

    @Autowired
    private CreditLogMapper creditLogMapper;

    @Autowired
    private CourseStatisticsMapper courseStatisticsMapper;

    /**
     * 查询课程学员表
     * 
     * @param conditions 课程学员表主键
     * @return 课程学员表
     */
    @Override
    public KnowledgeUser selectCourseUserById(Map<String,Object> conditions)
    {
        return courseUserMapper.selectCourseUserById(conditions);
    }

    @Override
    public int selectCourseUserCount(Map<String,Object> conditions)
    {
        return courseUserMapper.selectCourseUserCount(conditions);
    }

    @Override
    public int selectCourseUserSum(Map<String,Object> conditions)
    {
        return courseUserMapper.selectCourseUserSum(conditions);
    }

    @Override
    public KnowledgeUser joinCourseUser(String targetId, String from, String sourceId)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String time = DateUtils.getTime();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("targetId",targetId);
        conditions.put("userId",userId);
        conditions.put("companyId",companyId);
        KnowledgeUser knowledgeUserInfo = courseUserMapper.selectCourseUserById(conditions);
        Map<String,Object> userWhere = new HashMap<>();
        userWhere.put("targetId",targetId);
        userWhere.put("isDelete",0);
        userWhere.put("companyId",companyId);
        int requiredNum  = selectCourseUserCount(userWhere);
        if (knowledgeUserInfo == null){
            Map<String,Object> where = new HashMap<>();
            where.put("uuid",targetId);
            where.put("companyId",companyId);
            Knowledge info = courseService.selectCourseById(where);
            KnowledgeUser knowledgeUser = new KnowledgeUser();
            knowledgeUser.setTargetId(targetId);
            knowledgeUser.setTargetTitle(info.getTitle());
            knowledgeUser.setUserId(userId);
            knowledgeUser.setUserName(userInfo.getUserName());
            knowledgeUser.setCoursewareNum(info.getCoursewareNum());
            knowledgeUser.setSource(from);
            knowledgeUser.setCreateTime(time);
            knowledgeUser.setUpdateTime(time);
            knowledgeUser.setCompanyId(companyId);
            insertCourseUser(knowledgeUser);
            Knowledge knowledge = new Knowledge();
            knowledge.setUuid(targetId);
            knowledge.setRequiredNum(requiredNum + 1);
            knowledge.setUpdateTime(time);
            knowledge.setCompanyId(companyId);
            courseService.updateCourse(knowledge);
        } else {
            if (knowledgeUserInfo.getIsDelete() > 0){
                knowledgeUserInfo.setIsDelete(1);
                knowledgeUserInfo.setUpdateTime(time);
                updateCourseUser(knowledgeUserInfo);

                Knowledge knowledge = new Knowledge();
                knowledge.setUuid(targetId);
                knowledge.setRequiredNum(requiredNum + 1);
                if (knowledgeUserInfo.getLearnStatus().equals("finished")){
                    knowledge.setFinishRequiredNum(1);
                }
                knowledge.setUpdateTime(time);
                knowledge.setCompanyId(companyId);
                courseService.updateCourse(knowledge);
            }
        }
        conditions.put("isDelete",0);
        return courseUserMapper.selectCourseUserById(conditions);
    }

    /**
     * 同步课程进度进度
     */
    public void  syncCourseUserData(CoursewareLearn learnInfo){
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String userName  = userInfo.getUserName();
        String companyId = userInfo.getCompanyId();
        String courseId  = learnInfo.getCourseId();
        String time = DateUtils.getTime();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("userId", userId);
        conditions.put("targetId", courseId);
        conditions.put("companyId", companyId);
        KnowledgeUser knowledgeUser = selectCourseUserById(conditions);
        Boolean isNull = false;
        if (knowledgeUser == null){
            isNull = true;
            Map<String,Object> whereCourse = new HashMap<>();
            whereCourse.put("uuid",courseId);
            whereCourse.put("companyId",companyId);
            Knowledge knowledgeInfo = courseService.selectCourseById(whereCourse);
            knowledgeUser = new KnowledgeUser();
            knowledgeUser.setTargetId(courseId);
            knowledgeUser.setTargetTitle(knowledgeInfo.getTitle());
            knowledgeUser.setUserId(userId);
            knowledgeUser.setUserName(userInfo.getUserName());
            knowledgeUser.setCoursewareNum(knowledgeInfo.getCoursewareNum());
            knowledgeUser.setLearnStatus("learning");
            knowledgeUser.setProgress(0);
            knowledgeUser.setSource("course");
            knowledgeUser.setCreateTime(time);
            knowledgeUser.setUpdateTime(time);
            knowledgeUser.setCompanyId(companyId);
        }
        //更新课程进度
        if (!knowledgeUser.getLearnStatus().equals("finished") && learnInfo.getLearnTime() > 0) {
            int userLearnTime = 50;
            int courseLearnTime = 100;

            knowledgeUser.setLearnTime(userLearnTime); //统计学习时长
            int progress = 100;
            if (userLearnTime < courseLearnTime) {
                progress = (int) Math.floor((userLearnTime / (double) courseLearnTime) * 100);
            }
            if (progress == 100){
                knowledgeUser.setLearnStatus("finished");
            }
            knowledgeUser.setProgress(progress);
            knowledgeUser.setUpdateTime(DateUtils.getTime());
        } else if (knowledgeUser.getLearnStatus().equals("finished") && knowledgeUser.getProgress() != 100) {
            knowledgeUser.setProgress(100);
            knowledgeUser.setUpdateTime(DateUtils.getTime());
        }
        if (isNull){
            insertCourseUser(knowledgeUser);
        } else {
            updateCourseUser(knowledgeUser);
        }
        //验证是否获取奖励
        Map<String,Object> whereCourse = new HashMap<>();
        whereCourse.put("uuid",courseId);
        whereCourse.put("companyId",companyId);
        Knowledge knowledgeInfo = courseService.selectCourseById(whereCourse);
        if (knowledgeInfo.getEligible().equals("all")){
            if (knowledgeUser.getProgress() == 100 || knowledgeUser.getLearnStatus().equals("finished")){
                getUserRewards(courseId,userId,userName,companyId,"course", knowledgeInfo.getCertificate(), knowledgeInfo.getCredit());
            }
        } else {
            if (knowledgeUser.getProgress() == 100 || knowledgeUser.getLearnStatus().equals("finished")){
                getUserRewards(courseId,userId,userName,companyId,"course", knowledgeInfo.getCertificate(), knowledgeInfo.getCredit());
            }
        }
    }

    public void getUserRewards(String targetId,String userId,String userName,String companyId,String module,String certificate,Integer credit) {
        String time = DateUtils.getTime();
        if (credit > 0){
            //检查当前学员是否有获取过当前培训学分
            Map<String,Object> whereCredit = new HashMap<>();
            whereCredit.put("targetId",targetId);
            whereCredit.put("userId",userId);
            whereCredit.put("companyId",companyId);
            CreditLog logInfo = creditLogMapper.selectCreditLogById(whereCredit);
            if (logInfo != null){
                CreditLog creditLog = new CreditLog();
                creditLog.setUuid(UUID.randomUUID().toString());
                creditLog.setCredit(credit);
                creditLog.setUserId(userId);
                creditLog.setModule(module);
                creditLog.setUserName(userName);
                creditLog.setCreateTime(time);
                creditLog.setUpdateTime(time);
                creditLog.setCompanyId(companyId);
                creditLogMapper.insertCreditLog(creditLog);
            }
        }
    }



    /**
     * 查询课程学员表列表
     * 
     * @param conditions 课程学员表
     * @return 课程学员表
     */
    @Override
    public List<KnowledgeUser> selectCourseUserList(Map<String,Object> conditions)
    {
        return courseUserMapper.selectCourseUserList(conditions);
    }

    /**
     * 新增课程学员表
     * 
     * @param knowledgeUser 课程学员表
     * @return 结果
     */
    @Override
    public int insertCourseUser(KnowledgeUser knowledgeUser)
    {
        return courseUserMapper.insertCourseUser(knowledgeUser);
    }

    @Override
    public int insertCourseUserAll(List courseUser)
    {
        return courseUserMapper.insertCourseUserAll(courseUser);
    }

    /**
     * 修改课程学员表
     * 
     * @param knowledgeUser 课程学员表
     * @return 结果
     */
    @Override
    public int updateCourseUser(KnowledgeUser knowledgeUser)
    {
        return courseUserMapper.updateCourseUser(knowledgeUser);
    }

    @Override
    public int deleteCourseUser(KnowledgeUser knowledgeUser)
    {
        return courseUserMapper.deleteCourseUser(knowledgeUser);
    }

    /**
     * 批量删除课程学员表
     * 
     * @param ids 需要删除的课程学员表主键
     * @return 结果
     */
    @Override
    public int deleteCourseUserByIds(Long[] ids)
    {
        return courseUserMapper.deleteCourseUserByIds(ids);
    }

    /**
     * 删除课程学员表信息
     * 
     * @param id 课程学员表主键
     * @return 结果
     */
    @Override
    public int deleteCourseUserById(Long id)
    {
        return courseUserMapper.deleteCourseUserById(id);
    }

    @Override
    public List<CourseUserReport> getCourseUserStatistics(Map<String,Object> conditions)
    {
        List<CourseUserReport> list = courseStatisticsMapper.getCourseUserStatistics(conditions);
        return list;
    }

    @Override
    public List<CourseDeptReport> getCourseDeptStatistics(Map<String,Object> conditions)
    {
        List<CourseDeptReport> list = courseStatisticsMapper.getCourseDeptStatistics(conditions);
        for (CourseDeptReport items : list){
            if (items.getLearnNum() > 0 && items.getDeptUserNum() > 0){
                double learn = (double) items.getLearnNum() / items.getDeptUserNum() * 100;
                items.setLearnRate(String.valueOf(Math.round(learn * 100.0) / 100.0));
            }
            if (items.getFinishedNum() > 0 && items.getLearnNum() > 0){
                double finished = (double) items.getFinishedNum() / items.getLearnNum() * 100;
                items.setFinishRate(String.valueOf(Math.round(finished * 100.0) / 100.0));
            }
        }
        return list;
    }
}
