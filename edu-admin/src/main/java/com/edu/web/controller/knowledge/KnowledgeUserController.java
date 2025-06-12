package com.edu.web.controller.knowledge;

import java.util.*;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.core.page.TableDataInfo;
import com.edu.knowledge.domain.KnowledgeUser;
import com.edu.statistics.domain.CourseDeptReport;
import com.edu.statistics.domain.CourseUserReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edu.common.core.controller.BaseController;
import com.edu.knowledge.service.IKnowledgeUserService;

/**
 * 课程学员表Controller
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@RestController
@RequestMapping("/knowledge/knowledgeUser")
public class KnowledgeUserController extends BaseController
{
    @Autowired
    private IKnowledgeUserService courseUserService;

    /**
     * 个人维度查询
     */
    @GetMapping("/list")
    public TableDataInfo list(KnowledgeUser knowledgeUser)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Set<String> deptIds = knowledgeUser.getDeptIds();
        if (deptIds == null || deptIds.isEmpty()){
            deptIds = userInfo.getScopeIds();
        }
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("targetId",knowledgeUser.getTargetId());
        conditions.put("deptIds",deptIds);
        conditions.put("companyId",companyId);
        startPage();
        List<CourseUserReport> list = courseUserService.getCourseUserStatistics(conditions);
        return getDataTable(list);
    }

    /**
     * 部门维度查询
     */
    @GetMapping("/deptList")
    public TableDataInfo deptList(KnowledgeUser knowledgeUser)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Set<String> deptIds = knowledgeUser.getDeptIds();
        if (deptIds == null || deptIds.isEmpty()){
            deptIds = userInfo.getScopeIds();
        }
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("targetId",knowledgeUser.getTargetId());
        conditions.put("deptIds",deptIds);
        conditions.put("companyId",companyId);
        startPage();
        List<CourseDeptReport> list = courseUserService.getCourseDeptStatistics(conditions);
        return getDataTable(list);
    }

}
