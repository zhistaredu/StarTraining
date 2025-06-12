package com.edu.knowledge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.config.OssConfig;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.common.utils.StringUtils;
import com.edu.knowledge.domain.Knowledge;
import com.edu.knowledge.domain.vo.KnowledgeTypeCount;
import com.edu.service.IControlLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.knowledge.mapper.KnowledgeMapper;
import com.edu.knowledge.service.IKnowledgeService;

/**
 * 课程主表Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-10-01
 */
@Service
public class KnowledgeServiceImpl implements IKnowledgeService
{
    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private IControlLimitService visibleHomeService;


    /**
     * 查询课程主表
     * 
     * @param conditions 课程主表主键
     * @return 课程主表
     */
    @Override
    public Knowledge selectCourseById(Map<String,Object> conditions)
    {
        Knowledge info = knowledgeMapper.selectCourseById(conditions);
        if (StringUtils.isNotEmpty(info.getPicture())){
            info.setCdnPicture(ossConfig.getCdnUrl() + "/" + info.getPicture());
        }
        return info;
    }

    /**
     * 查询课程主表列表
     * 
     * @param conditions 课程主表
     * @return 课程主表
     */
    @Override
    public List<Knowledge> selectCourseList(Map<String,Object> conditions)
    {
        return knowledgeMapper.selectCourseList(conditions);
    }

    @Override
    public int selectCourseCount(Map<String,Object> conditions)
    {
        return knowledgeMapper.selectCourseCount(conditions);
    }

    @Override
    public KnowledgeTypeCount selectCourseTypeCount(Map<String,Object> conditions)
    {
        return knowledgeMapper.selectCourseTypeCount(conditions);
    }

    @Override
    public KnowledgeTypeCount selectCourseStatusCount(Map<String,Object> conditions)
    {
        return knowledgeMapper.selectCourseStatusCount(conditions);
    }

    @Override
    public KnowledgeTypeCount selectCourseGroupCount(Map<String,Object> conditions)
    {
        return knowledgeMapper.selectCourseGroupCount(conditions);
    }

    /**
     * 新增课程主表
     * 
     * @param knowledge 课程主表
     * @return 结果
     */
    @Override
    public int insertCourse(Knowledge knowledge)
    {
        return knowledgeMapper.insertCourse(knowledge);
    }

    /**
     * 修改课程主表
     * 
     * @param knowledge 课程主表
     * @return 结果
     */
    @Override
    public int updateCourse(Knowledge knowledge)
    {
        knowledge.setUpdateTime(DateUtils.getTime());
        return knowledgeMapper.updateCourse(knowledge);
    }

    /**
     * 发布考试
     *
     * @param knowledge 课程
     * @return 结果
     */
    @Override
    public void publishCourse(Knowledge knowledge)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        knowledge.setStatus(1);
        knowledge.setCompanyId(companyId);
        knowledge.setUpdateTime(DateUtils.getTime());
        knowledge.setPublishTime(DateUtils.getTime());
        knowledgeMapper.updateCourse(knowledge);
        Map<String,Object> where = new HashMap<>();
        where.put("uuid",knowledge.getUuid());
        where.put("companyId",companyId);
        Knowledge testInfo = selectCourseById(where);
        visibleHomeService.publishVisibleHome(knowledge.getUuid(),testInfo.getTitle());
    }

    /**
     * 关闭课程
     *
     * @param knowledge 课程
     * @return 结果
     */
    @Override
    public void closeCourse(Knowledge knowledge)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        knowledge.setStatus(2);
        knowledge.setCompanyId(companyId);
        knowledge.setUpdateTime(DateUtils.getTime());
        knowledgeMapper.updateCourse(knowledge);
    }


    /**
     * 删除课程信息
     *
     * @param id 课程主键
     * @return 结果
     */
    @Override
    public void deleteCourseById(String id)
    {
        Knowledge knowledge = new Knowledge();
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        String userId = userInfo.getUserId();
        knowledge.setUuid(id);
        knowledge.setStatus(-1);
        knowledge.setIsDelete(1);
        knowledge.setCompanyId(companyId);
        knowledge.setUpdateTime(DateUtils.getTime());
        knowledge.setUpdateUserId(userId);
        knowledgeMapper.updateCourse(knowledge);
    }
}
