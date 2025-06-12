package com.edu.statistics.service.impl;


import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.SecurityUtils;
import com.edu.knowledge.domain.vo.KnowledgeTypeCount;
import com.edu.knowledge.service.ICoursewareLearnService;
import com.edu.knowledge.service.IKnowledgeService;
import com.edu.statistics.domain.DataCenterStatistics;
import com.edu.statistics.domain.view.LearnProjectData;
import com.edu.statistics.domain.vo.LessonLearnScreenVo;
import com.edu.statistics.service.IDataCenterStatisticsService;
import com.edu.system.domain.vo.LoginInfoVo;
import com.edu.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataCenterStatisticsService implements IDataCenterStatisticsService {

    @Autowired
    private IKnowledgeService courseService;

    @Autowired
    private ISysLogininforService logininforService;

    @Autowired
    private ICoursewareLearnService CoursewareLearnService;

    @Override
    public DataCenterStatistics getDataCenter(){
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> where = new HashMap<>();
        where.put("status",1);
        where.put("isDelete",0);
        where.put("companyId",companyId);
        int courseCount = courseService.selectCourseCount(where);
        where.remove("status");
        where.put("unpublish",true);
        int noCourseNum = courseService.selectCourseCount(where);

        // 获取当前日期和时间
        LocalDateTime currentDate = LocalDateTime.now();
        // 从当前日期和时间中减去一个月
        LocalDateTime oneMonthAgo = currentDate.minus(1, ChronoUnit.MONTHS);
        // 将当前日期转换为 LocalDateTime 对象
        LocalDateTime currentDateTime = LocalDate.now().atStartOfDay();
        // 获取一个月前日期和时间的 Unix 时间戳（以秒为单位）
        long start = oneMonthAgo.toEpochSecond(ZoneOffset.UTC);
        // 获取当前日期和时间的 Unix 时间戳（以秒为单位）
        long now = currentDateTime.toEpochSecond(ZoneOffset.UTC);

        List<String> dateList = new ArrayList<>();
        List<Integer> loginLast = new ArrayList<>();
        List<Integer> lessonLast = new ArrayList<>();
        for (long i = start; i <= now; i += 86400) {
            LocalDate startDate = LocalDate.ofEpochDay(i / (24 * 60 * 60));
            DateTimeFormatter matter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startMatter = startDate.format(matter);
            dateList.add(startMatter);

            Map<String,Object> conditions = new HashMap<>();
            conditions.put("startTime",startMatter);
            conditions.put("companyId",companyId);
            LoginInfoVo loginInfo = logininforService.getLogininforCount(conditions);
            loginLast.add(loginInfo.getCount());
            LessonLearnScreenVo coursewareInfo = CoursewareLearnService.getLessonLearnScreenList(conditions);
            lessonLast.add(coursewareInfo.getCount() / 60);
        }
        DataCenterStatistics dataCenter = new DataCenterStatistics();
        dataCenter.setPubCourseNum(courseCount);
        dataCenter.setNoCourseNum(noCourseNum);
        dataCenter.setDateArr(dateList);
        dataCenter.setLoginList(loginLast);
        dataCenter.setLearnTimeList(lessonLast);
        return dataCenter;
    }

    @Override
    public LearnProjectData getLearningProject(){
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> whereCourse = new HashMap<>();
        whereCourse.put("isDelete",0);
        whereCourse.put("status",1);
        whereCourse.put("companyId",companyId);
        int courseCount = courseService.selectCourseCount(whereCourse);
        KnowledgeTypeCount knowledgeTypeCount = courseService.selectCourseTypeCount(whereCourse);
        // 使用BigDecimal进行除法运算，并保留两位小数
        BigDecimal totalResult = knowledgeTypeCount.getTotalDuration().divide(new BigDecimal("3600"), 2, RoundingMode.HALF_UP);;
        knowledgeTypeCount.setTotalDuration(totalResult);

        whereCourse.put("monthlyGrowth","yes");
        int monthlyGrowthCourse = courseService.selectCourseCount(whereCourse);
        knowledgeTypeCount.setCourseCount(courseCount);
        knowledgeTypeCount.setGrowthCourseCount(monthlyGrowthCourse);

        if (knowledgeTypeCount.getTotalCompleted() > 0){
            double courseRate = (double) knowledgeTypeCount.getTotalCompleted() / knowledgeTypeCount.getTotalLearners() * 100;
            // 使用String.format()保留两位小数
            knowledgeTypeCount.setFinishedRatio(String.format("%.2f", courseRate));
        }

        LearnProjectData learnProjectData = new LearnProjectData();
        learnProjectData.setCourse(knowledgeTypeCount);
        return learnProjectData;
    }
}
