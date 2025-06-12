package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.edu.common.core.domain.entity.SysUser;
import org.springframework.stereotype.Repository;

/**
 * 用户表 数据层
 * 
 * @author edu
 */
@Repository
public interface SysUserMapper
{
    /**
     * 根据条件分页查询用户列表
     * 
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(Map<String,Object> sysUser);

    /**
     * 查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserByList(Map<String,Object> sysUser);

    public List<SysUser> selectUserDeptPathList(Map<String,Object> sysUser);

    /**
     * 根据
     *
     * @param conditions 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserOnlyList(Map<String,String> conditions);

    /**
     * 根据条件分页查询未已配用户角色列表
     * 
     * @param conditions 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(Map<String,Object> conditions);

    /**
     * 根据条件分页查询未分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     * 
     * @param conditions 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Map<String,Object> conditions);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    public int insertUserAll(@Param("insertData") List insertData);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    public int updateUserStatus(SysUser user);

    /**
     * 修改用户头像
     * 
     * @param updateUser 用户名
     * @return 结果
     */
    public int updateUserAvatar(Map<String,Object> updateUser);

    /**
     * 重置用户密码
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(String userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(String[] userIds);

    /**
     * 校验用户名称是否唯一
     * 
     * @param userName 用户名称
     * @return 结果
     */
    public int checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);

    public List<SysUser> selectUserGroupConcat(Map<String,Object> conditions);
}
