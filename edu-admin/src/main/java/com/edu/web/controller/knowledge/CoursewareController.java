package com.edu.web.controller.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.knowledge.domain.CoursewareLearn;
import com.edu.knowledge.domain.view.LessonView;
import com.edu.knowledge.domain.vo.LessonProgress;
import com.edu.knowledge.service.ICoursewareLearnService;
import com.edu.service.IControlLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.knowledge.domain.Courseware;
import com.edu.knowledge.service.ICoursewareService;

/**
 * 课程课件表Controller
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@RestController
@RequestMapping("/knowledge/Courseware")
public class CoursewareController extends BaseController
{
    @Autowired
    private ICoursewareService coursewareService;

    @Autowired
    private ICoursewareLearnService coursewareLearnService;

    @Autowired
    private IControlLimitService controlLimitService;

    /**
     * 查询课程课件表列表
     */
    @GetMapping("/list")
    public AjaxResult list(Courseware courseware)
    {
        String companyId = getLoginUser().getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("courseId", courseware.getCourseId());
        conditions.put("isDelete",0);
        conditions.put("companyId",companyId);
        List<Courseware> list = coursewareService.selectCoursewareList(conditions);
        return AjaxResult.success(list);
    }

    /**
     * 新增课程课件表
     */
    @PostMapping
    public AjaxResult add(@RequestBody Courseware courseware)
    {
        return toAjax(coursewareService.insertCourseware(courseware));
    }

    /**
     * 修改课程课件表
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Courseware courseware)
    {
        return toAjax(coursewareService.updateCourseware(courseware));
    }

    /**
     * 批量添加课件
     */
    @PostMapping("/batchAddLesson")
    public AjaxResult batchAddLesson(@RequestBody Courseware courseware)
    {
        return toAjax(coursewareService.batchInsertCourseware(courseware));
    }

    /**
     * 获取课程课件表详细信息
     */
    @GetMapping("/info")
    public AjaxResult getInfo(Courseware courseware)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",courseware.getUuid());
        conditions.put("courseId",courseware.getCourseId());
        conditions.put("companyId",getLoginUser().getCompanyId());
        Courseware info = coursewareService.selectCoursewareById(conditions);
        return AjaxResult.success(info);
    }

    /**
     * 文档课件获取播放连接
     */
    @GetMapping(value = "docPlayUrl")
    public AjaxResult docPlayUrl(@RequestParam("accessToken") String accessToken,@RequestParam("refreshToken") String refreshToken) throws Exception {
        return AjaxResult.success(coursewareService.docPlayUrl(accessToken,refreshToken));
    }

    /**
     * 学员端课件列表
     */
    @GetMapping("/home/list")
    public AjaxResult homeList(Courseware courseware)
    {
        SysUser userInfo = getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String courseId  = courseware.getCourseId();
        String from = courseware.getFrom();
        String sourceId  = courseware.getSourceId();
        Map<String,Object> visibleWhere = new HashMap<>();
        visibleWhere.put("targetId",sourceId);
        visibleWhere.put("companyId",companyId);
        int count = controlLimitService.selectVisibleHomeCount(visibleWhere);//检查是否在管理范围之内
        List<Courseware> coursewareList = new ArrayList<>();
        if (count > 0){
            Map<String,Object> conditions = new HashMap<>();
            conditions.put("courseId", courseware.getCourseId());
            conditions.put("isDelete",0);
            conditions.put("companyId",companyId);
            coursewareList = coursewareService.selectCoursewareList(conditions);
            if (!coursewareList.isEmpty()){
                List<String> coursewareIds = coursewareList.stream()
                        .map(Courseware::getUuid)
                        .collect(Collectors.toList());
                Map<String,Object> userWhere = new HashMap<>();
                userWhere.put("courseId",courseId);
                userWhere.put("userId",userId);
                userWhere.put("coursewareIds",coursewareIds);
                userWhere.put("companyId",companyId);
                List<CoursewareLearn> learnList = coursewareLearnService.selectCoursewareLearnList(userWhere);

                Map<String, CoursewareLearn> learnMap = learnList.stream()
                        .filter(item -> item.getSource().equals(from)) // 过滤类型为 "course" 的元素
                        .collect(Collectors.toMap(CoursewareLearn::getCoursewareId, items -> items));

                coursewareList.get(0).setIsUnlocked(true);
                int lessonCount = coursewareList.size();
                for (int i = 0; i < lessonCount; i++) {
                    Courseware previousLesson = coursewareList.get(i);
                    String coursewareId = previousLesson.getUuid();
                    if (learnMap.containsKey(coursewareId)){
                        CoursewareLearn learnInfo = learnMap.get(coursewareId);
                        previousLesson.setProgress(learnInfo.getProgress());
                        int k = i + 1;
                        if (learnInfo.getProgress() == 100 && k < lessonCount){
                            coursewareList.get(k).setIsUnlocked(true);
                        }
                    }
                }
            }
        }
        return AjaxResult.success(coursewareList);
    }

    /**
     * 学员端课件详情
     */
    @GetMapping("/home/info/{uuid}")
    public AjaxResult homeInfo(@PathVariable("uuid") String uuid,
                               @RequestParam("courseId") String courseId) throws Exception {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("courseId",courseId);
        LessonView courseware = coursewareService.getCoursewareInfo(conditions);
        return AjaxResult.success(courseware);
    }

    /**
     * 新增课程课件学习
     */
    @PostMapping("/updateLearnProgress")
    public AjaxResult updateLearnProgress(@RequestBody Map<String,Object> params)
    {
        LessonProgress lessonProgress = new LessonProgress();
        lessonProgress.setCoursewareId((String) params.get("coursewareId"));
        lessonProgress.setCourseId((String) params.get("courseId"));
        lessonProgress.setProgressBar(Float.parseFloat((String) params.get("progressBar")));
        CoursewareLearn learnInfo = coursewareLearnService.updateCoursewareLearnProgress(lessonProgress);
        AjaxResult result = new AjaxResult();
        result.put("progressBar",learnInfo.getProgressBar());
        result.put("progress",learnInfo.getProgress());
        result.put("open",learnInfo.getOpen());
        return AjaxResult.success(result);
    }
}
