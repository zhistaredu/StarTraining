package com.edu.web.controller.knowledge;

import java.util.*;

import com.edu.common.config.OssConfig;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.knowledge.domain.CoursewareLearn;
import com.edu.knowledge.domain.KnowledgeUser;
import com.edu.knowledge.domain.Knowledge;
import com.edu.knowledge.domain.view.KnowledgeDetailView;
import com.edu.knowledge.domain.view.KnowledgeUserDetailView;
import com.edu.knowledge.service.ICoursewareLearnService;
import com.edu.knowledge.service.ICoursewareService;
import com.edu.knowledge.service.IKnowledgeUserService;
import com.edu.domain.KnowledgeControlLimit;
import com.edu.domain.ControlLimit;
import com.edu.service.IControlLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edu.common.annotation.Log;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.enums.BusinessType;
import com.edu.knowledge.service.IKnowledgeService;
import com.edu.common.core.page.TableDataInfo;

/**
 * 课程主表Controller
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@RestController
@RequestMapping("/knowledge/knowledge")
public class KnowledgeController extends BaseController
{
    @Autowired
    private IKnowledgeService knowledgeService;

    @Autowired
    private IKnowledgeUserService knowledgeUserService;

    @Autowired
    private IControlLimitService visibleHomeService;

    @Autowired
    private ICoursewareService coursewareService;

    @Autowired
    private ICoursewareLearnService coursewareLearnService;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 查询课程主表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Knowledge knowledge)
    {
        Map<String,Object>  conditions = new HashMap<>();
        conditions.put("status", knowledge.getStatus());
        conditions.put("categoryId", knowledge.getCategoryId());
        conditions.put("companyId",getLoginUser().getCompanyId());
        startPage();
        List<Knowledge> list = knowledgeService.selectCourseList(conditions);
        for (Knowledge items : list){
            items.setImgUrl(ossConfig.getCdnUrl() + "/" +  items.getPicture());
        }
        return getDataTable(list);
    }

    /**
     * 获取课程主表详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",getLoginUser().getCompanyId());
        Knowledge info = knowledgeService.selectCourseById(conditions);
        return AjaxResult.success(info);
    }

    /**
     * 新增课程主表
     */
    @Log(title = "课程主表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Knowledge knowledge)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        knowledge.setUuid(UUID.randomUUID().toString());
        knowledge.setCreateUserId(userInfo.getUserId());
        knowledge.setCreateTime(DateUtils.getTime());
        knowledge.setCompanyId(companyId);
        knowledgeService.insertCourse(knowledge);
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid", knowledge.getUuid());
        conditions.put("companyId",companyId);
        return AjaxResult.success(knowledgeService.selectCourseById(conditions));
    }

    /**
     * 修改课程主表
     */
    @Log(title = "课程主表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Knowledge knowledge)
    {
        SysUser userInfo = getLoginUser();
        knowledge.setCompanyId(userInfo.getCompanyId());
        return toAjax(knowledgeService.updateCourse(knowledge));
    }

    /**
     * 发布课程
     */
    @Log(title = "课程发布", businessType = BusinessType.UPDATE)
    @GetMapping("/publish/{uuid}")
    public AjaxResult publish(@PathVariable String uuid)
    {
        Knowledge knowledge = new Knowledge();
        knowledge.setUuid(uuid);
        knowledgeService.publishCourse(knowledge);
        return toAjax(1);
    }

    /**
     *关闭课程
     */
    @Log(title = "关闭课程", businessType = BusinessType.UPDATE)
    @GetMapping("/close/{uuid}")
    public AjaxResult close(@PathVariable String uuid)
    {
        Knowledge knowledge = new Knowledge();
        knowledge.setUuid(uuid);
        knowledgeService.closeCourse(knowledge);
        return toAjax(1);
    }

    /**
     * 删除课程
     */
    @Log(title = "删除课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id)
    {
        knowledgeService.deleteCourseById(id);
        return toAjax(1);
    }

    /**
     * 查询数据可见范围列表
     */
    @GetMapping("/controlLimit")
    public AjaxResult controlLimit(@RequestParam(value = "targetId") String targetId)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("targetId",targetId);
        conditions.put("companyId",getLoginUser().getCompanyId());
        Map<String,Object> list = visibleHomeService.selectVisibleHomeManageList(conditions);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据可见范围
     */
    @PostMapping("/controlLimit")
    public AjaxResult controlLimit(@RequestBody ControlLimit controlLimit)
    {
        String uuid = controlLimit.getUuid();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("companyId",getLoginUser().getCompanyId());
        Knowledge info = knowledgeService.selectCourseById(conditions);
        Map<String,Object> data = new HashMap<>();
        data.put("targetId",uuid);
        data.put("targetTitle",info.getTitle());
        data.put("status",info.getStatus());
        data.put("data", controlLimit.getData());
        visibleHomeService.insertVisibleHome(data);
        knowledgeService.updateCourse(info);
        return toAjax(1);
    }

    /**
     * 学员端课程列表
     * @param knowledge
     * @return
     */
    @GetMapping("/home/list")
    public TableDataInfo homeList(Knowledge knowledge)
    {
        String companyId = getLoginUser().getCompanyId();
        String userId    = getLoginUser().getUserId();
        Map<String,Object>  conditions = new HashMap<>();
        conditions.put("keyword", knowledge.getKeyword());
        conditions.put("categoryId", knowledge.getCategoryId());
        conditions.put("learnStatus", knowledge.getLearnStatus());
        conditions.put("lectorId", knowledge.getLectorId());
        conditions.put("userId",userId);
        conditions.put("companyId",companyId);
        startPage();
        List<KnowledgeControlLimit> list = visibleHomeService.selectVisibleCourseHomeList(conditions);
        for (KnowledgeControlLimit items : list){
            items.setImgUrl(ossConfig.getCdnUrl() + "/" +  items.getPicture());
        }
        return getDataTable(list);
    }

    /**
     * 学员端课程详情
     * @param uuid
     * @return
     */
    @GetMapping("/home/info/{uuid}")
    public AjaxResult homeInfo(@PathVariable("uuid") String uuid)
    {
        KnowledgeDetailView knowledgeDetailView = new KnowledgeDetailView();
        KnowledgeUserDetailView knowledgeUserDetailView = new KnowledgeUserDetailView();

        SysUser userInfo = getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("uuid",uuid);
        conditions.put("status",1);
        conditions.put("companyId",companyId);
        Knowledge info = knowledgeService.selectCourseById(conditions);
        if (info == null){
            return AjaxResult.error(300,"访问资源不存在，请联系管理员");
        }
        Map<String,Object> visibleWhere = new HashMap<>();
        visibleWhere.put("targetId",uuid);
        visibleWhere.put("companyId",companyId);
        KnowledgeControlLimit visibleInfo = visibleHomeService.selectVisibleCourseHomeInfo(visibleWhere);
        if (visibleInfo == null){
            return AjaxResult.error(300,"不在学习范围，请联系管理员");
        }
        //课程调用接口
        String sourceId = uuid;
        //课程信息变更修改
        Map<String,Object> userWhere = new HashMap<>();
        userWhere.put("targetId",uuid);
        userWhere.put("userId",userId);
        userWhere.put("isDelete",0);
        userWhere.put("companyId",companyId);
        KnowledgeUser knowledgeUser = knowledgeUserService.selectCourseUserById(userWhere);
        if (knowledgeUser != null){
            if (knowledgeUser.getProgress() > 0){
                Map<String,Object> learnWhere = new HashMap<>();
                learnWhere.put("courseId",uuid);
                learnWhere.put("userId",userId);
                learnWhere.put("companyId",companyId);
                CoursewareLearn learnInfo = coursewareLearnService.selectLastLearnInfo(learnWhere);
                if (learnInfo != null){
                    Map<String,Object> lessonWhere = new HashMap<>();
                    lessonWhere.put("uuid",learnInfo.getCoursewareId());
                    lessonWhere.put("courseId",uuid);
                    lessonWhere.put("companyId",companyId);
                    knowledgeUserDetailView.setLastLearnData(coursewareService.selectCoursewareById(lessonWhere));
                }
            }
            knowledgeUserDetailView.setTargetId(knowledgeUser.getTargetId());
            knowledgeUserDetailView.setTargetTitle(knowledgeUser.getTargetTitle());
            knowledgeUserDetailView.setUserId(knowledgeUser.getUserId());
            knowledgeUserDetailView.setUserName(knowledgeUser.getUserName());
            knowledgeUserDetailView.setCoursewareNum(knowledgeUser.getCoursewareNum());
            knowledgeUserDetailView.setLearnStatus(knowledgeUser.getLearnStatus());
            knowledgeUserDetailView.setCoursewareFinishNum(knowledgeUser.getCoursewareFinishNum());
            knowledgeUserDetailView.setProgress(knowledgeUser.getProgress());
            knowledgeUserDetailView.setFinishTime(knowledgeUser.getFinishTime());
        }

        if (knowledgeUserDetailView.getTargetId() == null){
            knowledgeUserDetailView = null;
        }

        knowledgeDetailView.setUuid(info.getUuid());
        knowledgeDetailView.setTitle(info.getTitle());
        knowledgeDetailView.setCredit(info.getCredit());
        knowledgeDetailView.setCategoryId(info.getCategoryId());
        knowledgeDetailView.setCategoryTitle(info.getCategoryTitle());
        knowledgeDetailView.setCdnPicture(ossConfig.getCdnUrl() + "/" +  info.getPicture());
        knowledgeDetailView.setSpeed(info.getSpeed());
        knowledgeDetailView.setDrag(info.getDrag());
        knowledgeDetailView.setLearnMode(info.getLearnMode());
        knowledgeDetailView.setPublishTime(info.getPublishTime());
        knowledgeDetailView.setEligible(info.getEligible());
        knowledgeDetailView.setLearnProgress(info.getLearnProgress());

        AjaxResult ajax = AjaxResult.success();
        ajax.put("info", knowledgeDetailView);
        ajax.put("courseUser", knowledgeUserDetailView);
        return ajax;
    }
}
