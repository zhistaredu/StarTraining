package com.edu.service.impl;

import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.domain.*;
import com.edu.mapper.ControlLimitMapper;
import com.edu.service.IControlLimitService;
import com.edu.system.domain.vo.ViewVisibleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 数据可见范围Service业务层处理
 *
 * @author chuanzhiedu
 * @date 2023-01-23
 */
@Service
public class ControlLimitServiceImpl implements IControlLimitService
{
    @Autowired
    private ControlLimitMapper controlLimitMapper;


    /**
     * 查询数据可见范围
     *
     * @param id 数据可见范围主键
     * @return 数据可见范围
     */
    @Override
    public ControlLimit selectVisibleHomeById(Long id)
    {
        return controlLimitMapper.selectVisibleHomeById(id);
    }

    /**
     * 查询数据可见范围列表
     *
     * @param conditions 数据可见范围
     * @return 数据可见范围
     */
    @Override
    public List<ControlLimit> selectVisibleHomeList(Map<String,Object> conditions)
    {
        List<ControlLimit> list = controlLimitMapper.selectVisibleHomeList(conditions);
        return list;
    }

    @Override
    public Map<String,List<ControlLimit>> getVisibleScopeList(Map<String,Object> conditions){
        List<ControlLimit> deptList = new ArrayList<>();
        List<ControlLimit> userList = new ArrayList<>();
        Map<String,List<ControlLimit>> viewData = new HashMap<>();
        viewData.put("deptList",deptList);
        viewData.put("userList",userList);
        return viewData;
    }

    @Override
    public Map<String,Object> selectVisibleHomeManageList(Map<String,Object> conditions)
    {
        Map<String, List<ViewVisibleVo>> viewRequired = new HashMap<>();
        Map<String,Object> viewData = new HashMap<>();
        viewData.put("viewRequired",viewRequired);
        return viewData;
    }


    @Override
    public List<ControlLimitScope> selectVisibleList(String companyId,String table)
    {
        return controlLimitMapper.selectVisibleList(companyId,table);
    }

    @Override
    public List<KnowledgeControlLimit> selectVisibleCourseHomeList(Map<String,Object> conditions)
    {
        List<KnowledgeControlLimit> list = controlLimitMapper.selectVisibleCourseHomeList(conditions);
        return list;
    }

    @Override
    public KnowledgeControlLimit selectVisibleCourseHomeInfo(Map<String,Object> conditions)
    {
        KnowledgeControlLimit info = controlLimitMapper.selectVisibleCourseHomeInfo(conditions);
        return info;
    }

    /**
     * 查询数据可见范围列表
     *
     * @param conditions 数据可见范围
     * @return 数据可见范围
     */
    @Override
    public int selectVisibleHomeCount(Map<String,Object> conditions)
    {
        return controlLimitMapper.selectVisibleHomeCount(conditions);
    }

    @Override
    public int selectVisibleHomeJoinCount(Map<String,Object> conditions)
    {
        return controlLimitMapper.selectVisibleHomeJoinCount(conditions);
    }

    /**
     * 新增数据可见范围
     *
     * @param assignUserData 数据可见范围
     * @return 结果
     */
    @Override
    @Transactional
    public void insertVisibleHome(Map<String,Object> assignUserData)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String companyName = userInfo.getCompanyName();
        String targetId = (String) assignUserData.get("targetId");
        String targetTitle = (String) assignUserData.get("targetTitle");
        String time  = DateUtils.getTime();
        List<ControlLimit> insertData = new ArrayList<>();
        ControlLimit assignUser = new ControlLimit();
        assignUser.setHomeId("1");
        assignUser.setName(companyName);
        assignUser.setScopeType("dept");
        assignUser.setTargetId(targetId);
        assignUser.setTargetTitle(targetTitle);
        assignUser.setIsDelete(0);
        assignUser.setCreateTime(time);
        assignUser.setUpdateTime(time);
        assignUser.setCreateUserId(userId);
        assignUser.setUpdateUserId(userId);
        assignUser.setCompanyId(companyId);
        insertData.add(assignUser);
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("targetId",targetId);
        conditions.put("companyId",companyId);
        controlLimitMapper.deleteVisibleHomeById(conditions);
        controlLimitMapper.insertVisibleHomeAll(insertData);
    }

    @Override
    public int insertVisibleHomeAll(List<ControlLimit> insertData, String table){
       return controlLimitMapper.insertVisibleHomeAll(insertData);
    }

    @Override
    public void publishVisibleHome(String targetId,String targetTitle){
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String companyName = userInfo.getCompanyName();
        String time = DateUtils.getTime();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("targetId",targetId);
        conditions.put("companyId",companyId);
        int count = controlLimitMapper.selectVisibleHomeCount(conditions);
        if (count == 0){
            List<ControlLimit> insertData = new ArrayList<>();
            ControlLimit assignUser = new ControlLimit();
            assignUser.setHomeId("1");
            assignUser.setName(companyName);
            assignUser.setScopeType("dept");
            assignUser.setTargetId(targetId);
            assignUser.setTargetTitle(targetTitle);
            assignUser.setIsDelete(0);
            assignUser.setCreateTime(time);
            assignUser.setUpdateTime(time);
            assignUser.setCreateUserId(userId);
            assignUser.setUpdateUserId(userId);
            assignUser.setCompanyId(companyId);
            insertData.add(assignUser);
            controlLimitMapper.insertVisibleHomeAll(insertData);
        }
    }

    /**
     * 修改数据可见范围
     *
     * @param controlLimit 数据可见范围
     * @return 结果
     */
    @Override
    public int updateVisibleHome(ControlLimit controlLimit)
    {
        controlLimit.setUpdateTime(DateUtils.getTime());
        return controlLimitMapper.updateVisibleHome(controlLimit);
    }

    /**
     * 批量删除数据可见范围
     *
     * @param ids 需要删除的数据可见范围主键
     * @return 结果
     */
    @Override
    public int deleteVisibleHomeByIds(Long[] ids)
    {
        return controlLimitMapper.deleteVisibleHomeByIds(ids);
    }

    /**
     * 删除数据可见范围信息
     *
     * @param conditions 数据可见范围主键
     * @return 结果
     */
    @Override
    public int deleteVisibleHomeById(Map<String,Object> conditions)
    {
        return controlLimitMapper.deleteVisibleHomeById(conditions);
    }
}
