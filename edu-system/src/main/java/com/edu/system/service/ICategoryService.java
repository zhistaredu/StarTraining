package com.edu.system.service;

import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.TreeSelect;
import com.edu.common.core.domain.entity.Category;

/**
 * 资源分类Service接口
 * 
 * @author chuanzhiedu
 * @date 2023-08-22
 */
public interface ICategoryService 
{
    /**
     * 查询资源分类
     * 
     * @param conditions 资源分类主键
     * @return 资源分类
     */
    public Category selectCategoryById(Map<String,Object> conditions);

    /**
     * 查询资源分类列表
     * 
     * @param conditions 资源分类
     * @return 资源分类集合
     */
    public List<Category> selectCategoryList(Map<String,Object> conditions);

    public int selectCategoryCount(Map<String,Object> conditions);

    public List<TreeSelect> buildCategoryTreeSelect(List<Category> categorys);

    public List<Category> buildCategoryTree(List<Category> categorys);

    /**
     * 新增资源分类
     * 
     * @param category 资源分类
     * @return 结果
     */
    public int insertCategory(Category category);

    public int insertCategoryAll(List insertData);

    /**
     * 修改资源分类
     * 
     * @param category 资源分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 批量删除资源分类
     * 
     * @param ids 需要删除的资源分类主键集合
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] ids);

    /**
     * 删除资源分类信息
     * 
     * @param uuid 资源分类主键
     * @return 结果
     */
    public int deleteCategoryById(String uuid);

    public void initializeCategory(String corpId);
}
