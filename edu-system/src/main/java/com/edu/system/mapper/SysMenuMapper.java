package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.edu.common.core.domain.entity.SysMenu;
import org.springframework.stereotype.Repository;

/**
 * 菜单表 数据层
 *
 * @author edu
 */
@Repository
public interface SysMenuMapper
{
    /**
     * 查询系统菜单列表
     *
     * @param where 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(Map<String,Object> where);

    /**
     * 根据用户所有权限
     *
     * @return 权限列表
     */
    public List<String> selectMenuPerms();

    /**
     * 根据用户查询系统菜单列表
     *
     * @param where 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuListByUserId(Map<String,Object> where);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByUserId(String userId);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeAll(Map<String,Object> conditions);

    /**
     * 根据用户ID查询菜单
     *
     * @param conditions 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Map<String,Object> conditions);

    /**
     * 根据角色ID查询菜单树信息
     * 
     * @param roleId 角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 选中菜单列表
     */
    public List<String> selectMenuListByRoleId(@Param("roleId") String roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    public SysMenu selectMenuById(String menuId);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int hasChildByMenuId(String menuId);

    /**
     * 新增菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int insertMenu(SysMenu menu);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenu menu);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(String menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @return 结果
     */
    public SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") String parentId);
}
