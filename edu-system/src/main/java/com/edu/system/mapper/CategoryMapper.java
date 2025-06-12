package com.edu.system.mapper;

import java.util.List;
import java.util.Map;

import com.edu.common.core.domain.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 资源分类Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-08-22
 */
@Repository
public interface CategoryMapper 
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

    /**
     * 新增资源分类
     * 
     * @param category 资源分类
     * @return 结果
     */
    public int insertCategory(Category category);

    public int insertCategoryAll(@Param("insertData")  List insertData);

    /**
     * 修改资源分类
     * 
     * @param category 资源分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 删除资源分类
     * 
     * @param conditions 资源分类主键
     * @return 结果
     */
    public int deleteCategoryById(Map<String,Object> conditions);

    /**
     * 批量删除资源分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] ids);
}
