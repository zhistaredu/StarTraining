package com.edu.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Validator;

import com.edu.common.core.domain.entity.*;
import com.edu.common.utils.DateUtils;
import com.edu.system.domain.vo.DeptUserVisibleVo;
import com.edu.system.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.edu.common.annotation.DataScope;
import com.edu.common.constant.UserConstants;
import com.edu.common.exception.ServiceException;
import com.edu.common.utils.SecurityUtils;
import com.edu.common.utils.StringUtils;
import com.edu.common.utils.bean.BeanValidators;
import com.edu.common.utils.spring.SpringUtils;
import com.edu.system.domain.SysUserPost;
import com.edu.system.domain.SysUserRole;
import com.edu.system.mapper.SysRoleMapper;
import com.edu.system.mapper.SysUserMapper;
import com.edu.system.mapper.SysUserRoleMapper;

/**
 * 用户 业务层处理
 *
 * @author edu
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

    /**
     * 根据条件分页查询用户列表
     *
     * @param conditions 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUserList(Map<String, Object> conditions) {
        return userMapper.selectUserList(conditions);
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUserByList(Map<String, Object> user) {
        return userMapper.selectUserByList(user);
    }

    @Override
    public List<SysUser> selectUserDeptPathList(Map<String, Object> user) {
        return userMapper.selectUserDeptPathList(user);
    }

    @Override
    public List<DeptUserVisibleVo> selectDeptUserList(Map<String, Object> user) {
        List<SysUser> list = userMapper.selectUserByList(user);
        List<DeptUserVisibleVo> data = new ArrayList<>();
        if (!list.isEmpty()){
            for (SysUser items : list){
                DeptUserVisibleVo deptUserVisibleVo = new DeptUserVisibleVo();
                deptUserVisibleVo.setUuid(items.getUserId());
                deptUserVisibleVo.setShowName(items.getUserName());
                deptUserVisibleVo.setDeptId(items.getDeptId());
                deptUserVisibleVo.setAvatar(items.getAvatar());
                deptUserVisibleVo.setType("user");
                data.add(deptUserVisibleVo);
            }
        }
        return data;
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param conditions 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectAllocatedList(Map<String,Object> conditions) {
        return userMapper.selectAllocatedList(conditions);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param conditions 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Map<String, Object> conditions) {
        return userMapper.selectUserById(conditions);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        String userId = StringUtils.isNull(user.getUserId()) ? "-1" : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        String userId = StringUtils.isNull(user.getUserId()) ? "-1" : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(String userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("userId", userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(conditions);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String deptId  = user.getDeptId();
        String companyId = userInfo.getCompanyId();
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setStatus(1);
        Map<String,Object> where = new HashMap<>();
        where.put("deptId",deptId);
        where.put("companyId",companyId);
        SysDept deptInfo = deptService.selectDeptById(where);
        user.setDeptIds(deptInfo.getDeptIdPath());
        user.setDeptNames(deptInfo.getDeptNamePath());
        user.setCompanyId(userInfo.getCompanyId());
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        List<SysUserRole> userRolelist = new ArrayList<SysUserRole>();
        for (String roleId : user.getRoleIds())
        {
            Map<String,Object> conditions = new HashMap<>();
            conditions.put("roleId",roleId);
            conditions.put("companyId",companyId);
            SysRole roleInfo = roleMapper.selectRoleById(conditions);
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            ur.setRoleType(roleInfo.getRoleKey());
            ur.setScopeType(3L);
            ur.setIsDelete(0);
            ur.setCompanyId(companyId);
            userRolelist.add(ur);
        }
        if (userRolelist.size() > 0){
            userRoleMapper.insertUserRoleAll(userRolelist);
        }
        return rows;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(SysUser user) {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String deptId = user.getDeptId();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> where = new HashMap<>();
        where.put("deptId",deptId);
        where.put("companyId",companyId);
        SysDept deptInfo = deptService.selectDeptById(where);
        user.setUpdateTime(DateUtils.getTime());
        user.setDeptIds(deptInfo.getDeptIdPath());
        user.setDeptNames(deptInfo.getDeptNamePath());
        user.setCompanyId(companyId);
        userMapper.updateUser(user);
        //更新角色表
        List<SysUserRole> userRolelist = new ArrayList<SysUserRole>();
        for (String roleId : user.getRoleIds())
        {
            Map<String,Object> conditions = new HashMap<>();
            conditions.put("roleId",roleId);
            conditions.put("companyId",companyId);
            SysRole roleInfo = roleMapper.selectRoleById(conditions);
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            ur.setRoleType(roleInfo.getRoleKey());
            ur.setScopeType(3L);
            ur.setIsDelete(0);
            ur.setCompanyId(companyId);
            userRolelist.add(ur);
        }
        if (userRolelist.size() > 0){
            userRoleMapper.insertUserRoleAll(userRolelist);
        }
        return 1;
    }

    @Override
    public int updateUserInfo(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(String userId, String[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUserStatus(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        String[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (String roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        String[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (String postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(String userId, String[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (String roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(String userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(String[] userIds) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, user);
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public List<SysUser> selectUserGroupConcat(Map<String,Object> conditions){
        List<SysUser> userList = userMapper.selectUserGroupConcat(conditions);
        return userList;
    }
}