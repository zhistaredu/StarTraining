package com.edu.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.knowledge.service.IKnowledgeService;
import com.edu.system.domain.LectorGrade;
import com.edu.system.service.ILectorGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edu.common.annotation.Log;
import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.common.enums.BusinessType;
import com.edu.system.domain.Lector;
import com.edu.system.service.ILectorService;
import com.edu.common.core.page.TableDataInfo;

/**
 * 讲师管理Controller
 * 
 * @author chuanzhiedu
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/lector/lector")
public class LectorController extends BaseController
{
    @Autowired
    private ILectorService lectorService;

    @Autowired
    private IKnowledgeService knowledgeService;

    @Autowired
    private ILectorGradeService lectorGradeService;

    /**
     * 查询讲师管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Lector lector)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("status",lector.getStatus());
        conditions.put("lectorGrade",lector.getLectorGrade());
        conditions.put("companyId",userInfo.getCompanyId());
        startPage();
        List<Lector> list = lectorService.selectLectorList(conditions);
        for (Lector items : list){
            Map<String,Object> where = new HashMap<>();
            where.put("uuid",items.getLectorGrade());
            where.put("companyId",companyId);
            LectorGrade gradeInfo = lectorGradeService.selectLectorGradeById(where);
            items.setLectorGradeName(gradeInfo.getTitle());
        }
        return getDataTable(list);
    }

    @GetMapping("/lectorList")
    public AjaxResult lectorList(Lector lector)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",userInfo.getCompanyId());
        List<Lector> list = lectorService.selectLectorList(conditions);
        return AjaxResult.success(list);
    }

    /**
     * 获取讲师管理详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("lectorId",uuid);
        conditions.put("companyId",userInfo.getCompanyId());
        return AjaxResult.success(lectorService.selectLectorById(conditions));
    }

    /**
     * 新增讲师管理
     */
    @Log(title = "讲师管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Lector lector)
    {
        return toAjax(lectorService.insertLector(lector));
    }

    /**
     * 修改讲师管理
     */
    @Log(title = "讲师管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Lector lector)
    {
        return toAjax(lectorService.updateLector(lector));
    }

    /**
     * 发布课程
     */
    @Log(title = "课程发布", businessType = BusinessType.UPDATE)
    @GetMapping("/publish/{uuid}")
    public AjaxResult publish(@PathVariable String uuid)
    {
        Lector lector = new Lector();
        lector.setLectorId(uuid);
        lectorService.publishLector(lector);
        return toAjax(1);
    }

    /**
     *关闭课程
     */
    @Log(title = "关闭课程", businessType = BusinessType.UPDATE)
    @GetMapping("/close/{uuid}")
    public AjaxResult close(@PathVariable String uuid)
    {
        Lector lector = new Lector();
        lector.setLectorId(uuid);
        lectorService.closeLector(lector);
        return toAjax(1);
    }

    /**
     * 删除讲师管理
     */
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id)
    {
        lectorService.deleteLectorById(id);
        return toAjax(1);
    }

    /**
     * 学员端讲师列表
     */
    @GetMapping("/home/list")
    public TableDataInfo homeList(Lector lector)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("lectorGrade",lector.getLectorGrade());
        conditions.put("companyId",companyId);
        startPage();
        List<Lector> list = lectorService.selectLectorList(conditions);
        for (Lector items : list){
            Map<String,Object> where = new HashMap<>();
            where.put("uuid",items.getLectorGrade());
            where.put("companyId",companyId);
            LectorGrade gradeInfo = lectorGradeService.selectLectorGradeById(where);
            items.setLectorGradeName(gradeInfo.getTitle());
            //统计课程数量
            Map<String,Object> whereLector = new HashMap<>();
            whereLector.put("lectorId",items.getLectorId());
            whereLector.put("status",1);
            whereLector.put("isDelete",0);
            whereLector.put("companyId",companyId);
            int courseCount = knowledgeService.selectCourseCount(whereLector);
            items.setCourseCount(courseCount);
        }
        return getDataTable(list);
    }

    /**
     * 学员端讲师详情
     */
    @GetMapping(value = "/home/info/{uuid}")
    public AjaxResult getHomeInfo(@PathVariable("uuid") String uuid)
    {
        SysUser userInfo = getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("lectorId",uuid);
        conditions.put("companyId",companyId);
        Lector info = lectorService.selectLectorById(conditions);
        Map<String,Object> where = new HashMap<>();
        where.put("uuid",info.getLectorGrade());
        where.put("companyId",companyId);
        LectorGrade gradeInfo = lectorGradeService.selectLectorGradeById(where);
        info.setLectorGradeName(gradeInfo.getTitle());
        //统计课程数量
        Map<String,Object> whereLector = new HashMap<>();
        whereLector.put("lectorId",uuid);
        whereLector.put("status",1);
        whereLector.put("isDelete",0);
        whereLector.put("companyId",companyId);
        int courseCount = knowledgeService.selectCourseCount(whereLector);
        info.setCourseCount(courseCount);
        return AjaxResult.success(info);
    }


}
