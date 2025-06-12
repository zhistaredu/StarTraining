package com.edu.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.edu.system.domain.SysUserRole;
import com.edu.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.common.constant.Constants;
import com.edu.common.constant.UserConstants;
import com.edu.common.core.domain.TreeSelect;
import com.edu.common.core.domain.entity.SysMenu;
import com.edu.common.core.domain.entity.SysRole;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.SecurityUtils;
import com.edu.common.utils.StringUtils;
import com.edu.system.domain.vo.MetaVo;
import com.edu.system.domain.vo.RouterVo;
import com.edu.system.mapper.SysMenuMapper;
import com.edu.system.mapper.SysRoleMapper;
import com.edu.system.mapper.SysRoleMenuMapper;
import com.edu.system.service.ISysMenuService;

/**
 * 菜单 业务层处理
 * 
 * @author edu
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private ISysUserRoleService userRoleService;

    /**
     * 根据用户查询系统菜单列表
     *
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList()
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        String userId    = userInfo.getUserId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("roleKey","admin");
        conditions.put("userId",userId);
        conditions.put("companyId",companyId);
        SysUserRole userRoleInfo = userRoleService.selectUserRoleInfo(conditions);
        List<SysMenu> menuList = null;
        Map<String,Object> where = new HashMap<>();
        if (userRoleInfo != null && userRoleInfo.getRoleType().equals("admin")){
            menuList = menuMapper.selectMenuList(where);
        } else {
            where.put("companyId",companyId);
            where.put("userId",userId);
            menuList = menuMapper.selectMenuListByUserId(where);
        }
        return menuList;
    }

    /**
     * 根据用户查询系统菜单列表
     * 
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(String userId,String roleId)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("roleId",roleId);
        conditions.put("companyId",companyId);
        SysRole roleInfo = roleMapper.selectRoleById(conditions);
        List<SysMenu> menuList = null;
        Map<String,Object> where = new HashMap<>();
        if (roleInfo != null && roleInfo.getRoleKey().equals("admin")){
            where.put("isSuper",0);
            menuList = menuMapper.selectMenuList(where);
        } else {
            where.put("companyId",companyId);
            where.put("userId",userId);
            where.put("rileId",roleId);
            menuList = menuMapper.selectMenuListByUserId(where);
        }
        return menuList;
    }

    /**
     * 查询系统菜单列表
     * 
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, String userId)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",companyId);
        SysRole roleInfo = roleMapper.selectRoleById(conditions);
        List<SysMenu> menuList = null;
        Map<String,Object> where = new HashMap<>();
        if (roleInfo != null && roleInfo.getRoleKey().equals("admin")){
            where.put("isSuper",0);
            menuList = menuMapper.selectMenuList(where);
        } else {
            where.put("companyId",companyId);
            where.put("userId",userId);
            menuList = menuMapper.selectMenuListByUserId(where);
        }
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(String userId)
    {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     * 
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(String userId)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        //查询是否为主管理员
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",companyId);
        conditions.put("userId",userId);
        conditions.put("isDelete",0);
        conditions.put("roleType","admin");
        List<SysUserRole> userRoleList = userRoleService.selectUserRoleData(conditions);
        List<SysMenu> menus = null;
        conditions.remove("roleType");
        if (userRoleList.size() > 0){
            //主管理员查询所有
            menus = menuMapper.selectMenuTreeAll(conditions);
        } else {
            conditions.put("userId",userId);
            menus = menuMapper.selectMenuTreeByUserId(conditions);
        }
        return getChildPerms(menus, "0");
    }

    /**
     * 获取快捷入口菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<String> getQuickAccess(String userId)
    {
        List<String> params = new ArrayList<>();
        return params;
    }

    /**
     * 根据角色ID查询菜单树信息
     * 
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<String> selectMenuListByRoleId(String roleId)
    {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("companyId",SecurityUtils.getCompanyId());
        conditions.put("roleId",roleId);
        SysRole role = roleMapper.selectRoleById(conditions);
        return menuMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }

    /**
     * 构建前端路由所需要的菜单
     * 
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus)
    {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType()))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMenuFrame(menu))
            {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            else if (menu.getParentId().equals("0")  && isInnerLink(menu))
            {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/inner");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 构建前端所需要树结构
     * 
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        List<String> tempList = new ArrayList<String>();
        for (SysMenu dept : menus)
        {
            tempList.add(dept.getMenuId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();)
        {
            SysMenu menu = (SysMenu) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId()))
            {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus)
    {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据菜单ID查询信息
     * 
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(String menuId)
    {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 是否存在菜单子节点
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(String menuId)
    {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 查询菜单使用数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(String menuId)
    {
        int result = roleMenuMapper.checkMenuExistRole(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 新增保存菜单信息
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu)
    {
        menu.setMenuId(UUID.randomUUID().toString());
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu)
    {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 删除菜单管理信息
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(String menuId)
    {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu)
    {
        String menuId = StringUtils.isNull(menu.getMenuId()) ? "" : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && !info.getMenuId().equals(menuId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 获取路由名称
     * 
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu)
    {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     * 
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu)
    {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (!menu.getParentId().equals("0") && isInnerLink(menu))
        {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (menu.getParentId().equals("0") && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame()))
        {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     * 
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu))
        {
            component = menu.getComponent();
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && !menu.getParentId().equals("0") && isInnerLink(menu))
        {
            component = UserConstants.INNER_LINK;
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu))
        {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenu menu)
    {
        return menu.getParentId().equals("0") && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 是否为内链组件
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenu menu)
    {
        return menu.getIsFrame().equals(UserConstants.NO_FRAME) && StringUtils.ishttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     * 
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenu menu)
    {
        return !menu.getParentId().equals("0") && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 根据父节点的ID获取所有子节点
     * 
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, String parentId)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId().equals(parentId))
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     * 
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().equals(t.getMenuId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 内链域名特殊字符替换
     * 
     * @return
     */
    public String innerLinkReplaceEach(String path)
    {
        return StringUtils.replaceEach(path, new String[] { Constants.HTTP, Constants.HTTPS },
                new String[] { "", "" });
    }
}
