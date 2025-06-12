package com.edu.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.edu.common.core.domain.TreeSelect;
import com.edu.common.core.domain.entity.Category;
import com.edu.common.core.domain.entity.SysUser;
import com.edu.common.utils.DateUtils;
import com.edu.common.utils.SecurityUtils;
import com.edu.common.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.system.mapper.CategoryMapper;
import com.edu.system.service.ICategoryService;

/**
 * 资源分类Service业务层处理
 * 
 * @author chuanzhiedu
 * @date 2023-08-22
 */
@Service
public class CategoryServiceImpl implements ICategoryService 
{
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询资源分类
     * 
     * @param where 资源分类主键
     * @return 资源分类
     */
    @Override
    public Category selectCategoryById(Map<String,Object> where)
    {
        return categoryMapper.selectCategoryById(where);
    }

    /**
     * 查询资源分类列表
     * 
     * @param conditions 资源分类
     * @return 资源分类
     */
    @Override
    public List<Category> selectCategoryList(Map<String,Object> conditions)
    {
        return categoryMapper.selectCategoryList(conditions);
    }

    @Override
    public int selectCategoryCount(Map<String,Object> conditions)
    {
        return categoryMapper.selectCategoryCount(conditions);
    }

    @Override
    public List<TreeSelect> buildCategoryTreeSelect(List<Category> categorys)
    {
        List<Category> categoryTrees = buildCategoryTree(categorys);
        return categoryTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<Category> buildCategoryTree(List<Category> categorys)
    {
        List<Category> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<String>();
        for (Category category : categorys)
        {
            tempList.add(category.getUuid());
        }
        for (Iterator<Category> iterator = categorys.iterator(); iterator.hasNext();)
        {
            Category menu = iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId()))
            {
                recursionFn(categorys, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = categorys;
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<Category> list, Category t)
    {
        // 得到子节点列表
        List<Category> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Category tChild : childList)
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
    private List<Category> getChildList(List<Category> list, Category t)
    {
        List<Category> tlist = new ArrayList<>();
        Iterator<Category> it = list.iterator();
        while (it.hasNext())
        {
            Category n = it.next();
            if (n.getParentId().equals(t.getUuid()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Category> list, Category t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 新增资源分类
     * 
     * @param category 资源分类
     * @return 结果
     */
    @Override
    public int insertCategory(Category category)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        String time = DateUtils.getTime();
        String uuid = UUID.randomUUID().toString();
        String parentId = category.getParentId();
        if (parentId.equals("1")){
            category.setCategoryPath("1,"+uuid);
        } else {
            Map<String,Object> where = new HashMap<>();
            where.put("uuid",parentId);
            where.put("companyId",companyId);
            Category info = selectCategoryById(where);
            category.setCategoryPath(info.getCategoryPath()+","+uuid);
        }
        category.setUuid(uuid);
        category.setCreateTime(time);
        category.setUpdateTime(time);
        category.setCreateUserId(userId);
        category.setUpdateUserId(userId);
        category.setCompanyId(companyId);
        return categoryMapper.insertCategory(category);
    }

    /**
     * 修改资源分类
     * 
     * @param category 资源分类
     * @return 结果
     */
    @Override
    public int updateCategory(Category category)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String userId = userInfo.getUserId();
        String companyId = userInfo.getCompanyId();
        category.setUpdateTime(DateUtils.getTime());
        category.setUpdateUserId(userId);
        category.setCompanyId(companyId);
        return categoryMapper.updateCategory(category);
    }

    /**
     * 批量删除资源分类
     * 
     * @param ids 需要删除的资源分类主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(Long[] ids)
    {
        return categoryMapper.deleteCategoryByIds(ids);
    }

    /**
     * 删除资源分类信息
     * 
     * @param uuid 资源分类主键
     * @return 结果
     */
    @Override
    public int deleteCategoryById(String uuid)
    {
        SysUser userInfo = SecurityUtils.getLoginUser();
        String companyId = userInfo.getCompanyId();
        Category categoryInfo = new Category();
        categoryInfo.setUuid(uuid);
        categoryInfo.setIsDelete(1);
        categoryInfo.setCompanyId(companyId);
        return categoryMapper.updateCategory(categoryInfo);
    }

    @Override
    public int insertCategoryAll(List insertData){
        return categoryMapper.insertCategoryAll(insertData);
    }

    @Override
    public void initializeCategory(String corpId){
        Map<String,Object> where = new HashMap<>();
        where.put("companyId",corpId);
        Category info = categoryMapper.selectCategoryById(where);
        if (info == null){
            List<String> leveone = new ArrayList<>();
            Collections.addAll(leveone,"企业经营","经验分享","企业文化","职业素养","专业技能","通用工具");
            List<String> a = new ArrayList<>();
            Collections.addAll(a,"战略模块","运营模块","组织模块","领导模块");
            List<String> b = new ArrayList<>();
            Collections.addAll(b,"技术文档","行业方案","商务合作","宣传文案","售后服务","客户方案");
            List<String> c = new ArrayList<>();
            Collections.addAll(c,"公司介绍","规章制度","领导讲话","员工风采","员工感情");
            List<String> d = new ArrayList<>();
            Collections.addAll(d,"语言学习","人际沟通","商务礼仪","办公软件","职场心态","思维训练");
            List<String> e = new ArrayList<>();
            Collections.addAll(e,"生产研发","市场营销","人力资源","采购物流","财务管理","互联网");
            Map<String,List<String>> levetwo = new HashMap<>();
            levetwo.put("企业经营",a);
            levetwo.put("经验分享",b);
            levetwo.put("企业文化",c);
            levetwo.put("职业素养",d);
            levetwo.put("专业技能",e);

            String time = DateUtils.getTime();
            List<Category> data = new ArrayList<>();
            for (String title : leveone){
                String uuid = UUID.randomUUID().toString();
                Category category = new Category();
                category.setUuid(uuid);
                category.setTitle(title);
                category.setParentId("1");
                category.setCategoryPath("1," + uuid);
                category.setStatus(1);
                category.setIsDelete(0);
                category.setCreateTime(time);
                category.setUpdateTime(time);
                category.setCreateUserId(corpId);
                category.setUpdateUserId(corpId);
                category.setCompanyId(corpId);
                data.add(category);
                if (levetwo.containsKey(title)) {
                    for (String tl : levetwo.get(title)){
                        String uuidOne = UUID.randomUUID().toString();
                        Category cate = new Category();
                        cate.setUuid(uuidOne);
                        cate.setTitle(tl);
                        cate.setParentId(category.getUuid());
                        cate.setCategoryPath("1,"+ category.getUuid() + "," + uuidOne);
                        cate.setStatus(1);
                        cate.setIsDelete(0);
                        cate.setCreateTime(time);
                        cate.setUpdateTime(time);
                        cate.setCreateUserId(corpId);
                        cate.setUpdateUserId(corpId);
                        cate.setCompanyId(corpId);
                        data.add(cate);
                    }
                }
            }
            if (StringUtils.isNotEmpty(data)){
                categoryMapper.insertCategoryAll(data);
            }
        }
    }
}
