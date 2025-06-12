package com.edu.knowledge.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.edu.common.config.OssConfig;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.config.DmmUtil;
import com.edu.knowledge.domain.CoursewareLearn;
import com.edu.knowledge.domain.Knowledge;
import com.edu.knowledge.domain.MaterialLibrary;
import com.edu.knowledge.domain.view.LessonView;
import com.edu.knowledge.domain.vo.CoursewareJoinUser;
import com.edu.knowledge.domain.vo.LessonData;
import com.edu.knowledge.mapper.KnowledgeMapper;
import com.edu.knowledge.service.*;
import com.edu.statistics.domain.vo.LessonTypeCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.knowledge.mapper.CoursewareMapper;
import com.edu.knowledge.domain.Courseware;
import org.springframework.transaction.annotation.Transactional;

/**
 * 课程课件表Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Service
public class CoursewareServiceImpl implements ICoursewareService
{
    @Autowired
    private CoursewareMapper coursewareMapper;

    @Autowired
    private ICoursewareLearnService CoursewareLearnService;

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Autowired
    private IMaterialLibraryService materialLibraryService;

    @Autowired
    private IKnowledgeService courseService;

    @Autowired
    private IKnowledgeUserService courseUserService;

    @Autowired
    private DmmUtil DmmUtil;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 查询课程课件表
     * 
     * @param conditions 课程课件表主键
     * @return 课程课件表
     */
    @Override
    public Courseware selectCoursewareById(Map<String,Object> conditions)
    {
        return coursewareMapper.selectCoursewareById(conditions);
    }

    @Override
    public LessonView getCoursewareInfo(Map<String,Object> conditions) throws Exception {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String companyName = userInfo.getCompanyName();
        String coursewareId = (String) conditions.get("uuid");
        String courseId  = (String) conditions.get("courseId");
        Map<String,Object> whereLesson = new HashMap<>();
        whereLesson.put("courseId",courseId);
        whereLesson.put("uuid",coursewareId);
        whereLesson.put("companyId",companyId);
        Courseware info = coursewareMapper.selectCoursewareById(whereLesson);
        Map<String,Object> whereCourse = new HashMap<>();
        whereCourse.put("uuid",courseId);
        whereCourse.put("companyId",companyId);
        String syncLearn = "close";
        Knowledge knowledgeInfo = knowledgeMapper.selectCourseById(whereCourse);
        syncLearn = knowledgeInfo.getSyncLearn();
        Map<String,Object> learnWhere = new HashMap<>();
        learnWhere.put("coursewareId",coursewareId);
        learnWhere.put("userId",userId);
        learnWhere.put("courseId",courseId);
        learnWhere.put("companyId",companyId);
        CoursewareLearn learnInfo = CoursewareLearnService.selectCoursewareLearnById(learnWhere);
        Float learnTime = (float) 0;
        Float progressBar = 0.00F;
        Integer progress = 0;
        if (learnInfo != null){
            learnTime = learnInfo.getLearnTime();
            progressBar = learnInfo.getProgressBar();
            progress = learnInfo.getProgress();
        }
        Boolean joinOpen = false;
        if (syncLearn.equals("open")){
            conditions.put("coursewareId",coursewareId);
            conditions.put("companyId",companyId);
            conditions.put("userId",userId);
            //获取学习进度最多的课件记录
            conditions.put("maxLearnTimeOne","yes");
            CoursewareLearn coursewareLearn = CoursewareLearnService.selectCoursewareLearnById(conditions);
            //与当前学习记录比较大小，取最大值
            if (coursewareLearn != null && learnTime < coursewareLearn.getLearnTime()){
                joinOpen = true;
                learnTime = coursewareLearn.getLearnTime();
                progressBar = coursewareLearn.getProgressBar();
                progress = coursewareLearn.getProgress();
            }
        }
        LessonView lesson = new LessonView();
        lesson.setUuid(coursewareId);
        lesson.setTitle(info.getTitle());
        lesson.setCourseId(info.getCourseId());
        lesson.setLength(info.getLength());
        if (learnInfo == null){
            learnInfo = new CoursewareLearn();
            learnInfo.setEventId(UUID.randomUUID().toString());
            learnInfo.setUserId(userId);
            learnInfo.setUserName(userInfo.getUserName());
            learnInfo.setCourseId(courseId);
            learnInfo.setCoursewareId(coursewareId);
            learnInfo.setCoursewareTitle(info.getTitle());
            learnInfo.setProgressBar(progressBar);
            learnInfo.setProgress(progress);
            learnInfo.setLearnTime(learnTime);
            learnInfo.setLength(info.getLength());
            learnInfo.setStartTime(DateUtils.getTime());
            learnInfo.setCompanyId(companyId);
            CoursewareLearnService.insertCoursewareLearn(learnInfo);
            learnInfo.setCompanyName(companyName);//水印
        } else {
            learnInfo.setLearnTime(learnTime);
            learnInfo.setProgressBar(progressBar);
            learnInfo.setProgress(progress);
            CoursewareLearnService.updateCoursewareLearn(learnInfo);
            learnInfo.setCompanyName(companyName);
        }
        lesson.setProgressBar(progressBar);
        lesson.setProgress(progress);
        //如果有进度变化就更新课程进度
        if (joinOpen){
            //更新进度
            courseUserService.syncCourseUserData(learnInfo);
        }
        lesson.setProgressBar(learnInfo.getProgressBar());
        lesson.setProgress(learnInfo.getProgress());
        String type = info.getType();
        Map<String,Object> fileWhere = new HashMap<>();
        if (type.equals("video")){
            fileWhere.put("uuid",info.getUuid());
            fileWhere.put("companyId",companyId);
            MaterialLibrary fileInfo = materialLibraryService.selectMaterialLibraryById(fileWhere);
            String transCodeUri = ossConfig.getCdnUrl() + "/" + fileInfo.getObject();
            lesson.setTransCodeUri(transCodeUri);
        } else if (type.equals("document")) {
            fileWhere.put("uuid",info.getUuid());
            fileWhere.put("companyId",companyId);
            MaterialLibrary fileInfo = materialLibraryService.selectMaterialLibraryById(fileWhere);
            DmmUtil.createImmClient();
            Map<String,Object> respond = DmmUtil.getWebOfficeToken(fileInfo.getObject(),userId);
            lesson.setTransCodeUri((String) respond.get("webofficeURL"));
            lesson.setAccessToken((String) respond.get("accessToken"));
            lesson.setRefreshToken((String) respond.get("refreshToken"));
        }
        Courseware nextLesson = selectNextLesson(courseId,info.getWeight());
        lesson.setNextCourseware(nextLesson);
        return lesson;
    }

    @Override
    public Map<String,Object> docPlayUrl(String accessToken,String refreshToken) throws Exception {
        DmmUtil.createImmClient();
        return DmmUtil.refreshWebofficeToken(accessToken,refreshToken);
    }

    /**
     * 查询课程课件表列表
     * 
     * @param conditions 课程课件表
     * @return 课程课件表
     */
    @Override
    public List<Courseware> selectCoursewareList(Map<String,Object> conditions)
    {
        return coursewareMapper.selectCoursewareList(conditions);
    }

    @Override
    public List<CoursewareJoinUser> selectCoursewareJoinUserList(Map<String,Object> conditions)
    {
        return coursewareMapper.selectCoursewareJoinUserList(conditions);
    }

    @Override
    public Courseware selectNextLesson(String courseId, int weight)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> lessonWhere = new HashMap<>();
        lessonWhere.put("courseId",courseId);
        lessonWhere.put("companyId",companyId);
        lessonWhere.put("isDelete",0);
        lessonWhere.put("weight",weight+1);
        Courseware nextLesson = selectCoursewareById(lessonWhere);
        return nextLesson;
    }

    /**
     * 课件数量
     * @param conditions
     * @return
     */
    @Override
    public int selectCoursewareCount(Map<String,Object> conditions)
    {
        return coursewareMapper.selectCoursewareCount(conditions);
    }

    @Override
    public LessonTypeCount selectCoursewareTypeCount(Map<String,Object> conditions)
    {
        return coursewareMapper.selectCoursewareTypeCount(conditions);
    }

    /**
     * 课件时长
     * @param conditions
     * @return
     */
    @Override
    public int selectCoursewareLength(Map<String,Object> conditions)
    {
        return coursewareMapper.selectCoursewareLength(conditions);
    }

    /**
     * 新增课程课件表
     * 
     * @param courseware 课程课件表
     * @return 结果
     */
    @Override
    public int insertCourseware(Courseware courseware)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        String courseId  = courseware.getCourseId();
        String type = courseware.getType();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("courseId",courseId);
        conditions.put("companyId",companyId);
        int count = selectCoursewareCount(conditions);
        Map<String,Object> where = new HashMap<>();
        where.put("uuid", courseware.getUuid());
        where.put("companyId",companyId);
        MaterialLibrary fileInfo = materialLibraryService.selectMaterialLibraryById(where);
        courseware.setUuid(courseware.getUuid());
        courseware.setWeight(count + 1);
        courseware.setType(type);
        courseware.setLength(fileInfo.getLength());
        courseware.setFileType(fileInfo.getFileType());
        courseware.setCreateTime(DateUtils.getTime());
        courseware.setCreateUserId(userInfo.getUserId());
        courseware.setCompanyId(companyId);
        int result = coursewareMapper.insertCourseware(courseware);
        //更新课程课件数量
        conditions.put("isDelete",0);
        int coursewareNum = selectCoursewareCount(conditions);
        int length = selectCoursewareLength(conditions);
        Knowledge knowledgeInfo = new Knowledge();
        knowledgeInfo.setUuid(courseId);
        knowledgeInfo.setCompanyId(companyId);
        knowledgeInfo.setCoursewareNum(coursewareNum);
        knowledgeInfo.setDuration(length);
        courseService.updateCourse(knowledgeInfo);
        return result;
    }

    @Override
    public int insertCoursewareInfo(Courseware courseware){
        return coursewareMapper.insertCourseware(courseware);
    }

    /**
     * 批量插入课件
     *
     * @param courseware 批量插入课件
     * @return 结果
     */
    @Override
    @Transactional
    public int batchInsertCourseware(Courseware courseware)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId    = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String time      = DateUtils.getTime();
        String courseId  = courseware.getCourseId();
        String lessonValue = courseware.getLessonValue();
        List<LessonData> lessons = JSON.parseArray(lessonValue, LessonData.class);
        //删除数据
        batchDeleteCourseware(courseId);
        int i = 0;
        List<Courseware> insertData = new ArrayList<>();
        for (LessonData lesson : lessons){
            i++;
            Courseware coursewareInfo = new Courseware();
            coursewareInfo.setUuid(lesson.getUuid());
            coursewareInfo.setCourseId(courseId);
            coursewareInfo.setTitle(lesson.getTitle());
            coursewareInfo.setType(lesson.getType());
            coursewareInfo.setIsDelete(0);
            coursewareInfo.setWeight(i);
            coursewareInfo.setLength(lesson.getLength());
            coursewareInfo.setCreateTime(time);
            coursewareInfo.setUpdateTime(time);
            coursewareInfo.setCreateUserId(userId);
            coursewareInfo.setUpdateUserId(userId);
            coursewareInfo.setCompanyId(companyId);
            insertData.add(coursewareInfo);
        }
        if (!insertData.isEmpty()){
            coursewareMapper.insertCoursewareAll(insertData);
        }
        //获取课件数量和课件时长
        Map<String,Object> where = new HashMap<>();
        where.put("courseId",courseId);
        where.put("companyId",companyId);
        where.put("isDelete",0);
        int count = selectCoursewareCount(where);
        int length = selectCoursewareLength(where);
        Knowledge knowledge = new Knowledge();
        knowledge.setUuid(courseId);
        knowledge.setCoursewareNum(count);
        knowledge.setDuration(length);
        knowledge.setUpdateTime(time);
        knowledge.setUpdateUserId(userId);
        knowledge.setCompanyId(companyId);
        return knowledgeMapper.updateCourse(knowledge);
    }

    public void batchDeleteCourseware(String courseId)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        //删除课件
        Courseware courseware = new Courseware();
        courseware.setCourseId(courseId);
        courseware.setIsDelete(1);
        courseware.setUpdateTime(DateUtils.getTime());
        courseware.setUpdateUserId(userId);
        courseware.setCompanyId(companyId);
        coursewareMapper.updateCourseware(courseware);
        //删除课件对应学习记录
        CoursewareLearn coursewareLearn = new CoursewareLearn();
        coursewareLearn.setIsDelete(1);
        coursewareLearn.setUpdateTime(DateUtils.getTime());
        coursewareLearn.setCourseId(courseId);
        coursewareLearn.setStageId(courseId);
        coursewareLearn.setSourceId(courseId);
        coursewareLearn.setCompanyId(companyId);
        CoursewareLearnService.updateCoursewareLearn(coursewareLearn);
    }

    @Override
    public int updateCoursewareInfo(Courseware courseware){
        return coursewareMapper.updateCourseware(courseware);
    }

    /**
     * 修改课程课件表
     * 
     * @param courseware 课程课件表
     * @return 结果
     */
    @Override
    public int updateCourseware(Courseware courseware)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        courseware.setUpdateTime(DateUtils.getTime());
        courseware.setUpdateUserId(userInfo.getUserId());
        courseware.setCompanyId(companyId);
        return coursewareMapper.insertCourseware(courseware);
    }

    /**
     * 删除课程课件表信息
     * 
     * @param uuid 课程课件表主键
     * @return 结果
     */
    @Override
    public int deleteCoursewareById(String uuid)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",companyId);
        Courseware lessonInfo = selectCoursewareById(conditions);
        String courseId = lessonInfo.getCourseId();
        //删除课件
        Courseware courseware = new Courseware();
        courseware.setUuid(uuid);
        courseware.setIsDelete(1);
        courseware.setUpdateTime(DateUtils.getTime());
        courseware.setUpdateUserId(userId);
        courseware.setCompanyId(companyId);
        coursewareMapper.updateCourseware(courseware);
        //删除课件对应学习记录
        CoursewareLearn coursewareLearn = new CoursewareLearn();
        coursewareLearn.setCoursewareId(uuid);
        coursewareLearn.setIsDelete(1);
        coursewareLearn.setUpdateTime(DateUtils.getTime());
        coursewareLearn.setCompanyId(companyId);
        CoursewareLearnService.updateCoursewareLearn(coursewareLearn);
        //获取课件数量和课件时长
        Map<String,Object> where = new HashMap<>();
        where.put("courseId",courseId);
        where.put("companyId",companyId);
        where.put("isDelete",0);
        int count = selectCoursewareCount(where);
        int length = selectCoursewareLength(where);
        Knowledge knowledge = new Knowledge();
        knowledge.setUuid(courseId);
        knowledge.setCoursewareNum(count);
        knowledge.setDuration(length);
        knowledge.setUpdateTime(DateUtils.getTime());
        knowledge.setCompanyId(companyId);
        return knowledgeMapper.updateCourse(knowledge);
    }

    @Override
    public int CoursewareListSort(Map<String,Object> params){
        List<String> sortIds = (List<String>) params.get("sortIds");
        String courseId = (String) params.get("courseId");

        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> where = new HashMap<>();
        where.put("courseId",courseId);
        where.put("isDelete",0);
        where.put("companyId",companyId);
        List<Courseware> coursewareList = selectCoursewareList(where);
        Map<String, Courseware> lessonMap = coursewareList.stream()
                .collect(Collectors.toMap(Courseware::getUuid, lesson -> lesson));
        int sort = 1;
        for (String coursewareId : sortIds){
            if (lessonMap.containsKey(coursewareId)){
                Courseware lesson = lessonMap.get(coursewareId);
                if (lesson.getWeight() != sort){
                    Courseware courseware = new Courseware();
                    courseware.setUuid(lesson.getUuid());
                    courseware.setWeight(sort);
                    courseware.setUpdateTime(DateUtils.getTime());
                    courseware.setCompanyId(companyId);
                    coursewareMapper.updateCourseware(courseware);
                }
                sort++;
            }
        }
        return 1;
    }
}
