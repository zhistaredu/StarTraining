package com.edu.knowledge.mapper;

import java.util.List;
import java.util.Map;

import com.edu.knowledge.domain.MaterialLibrary;
import org.springframework.stereotype.Repository;

/**
 * 素材库Mapper接口
 * 
 * @author chuanzhiedu
 * @date 2023-10-14
 */
@Repository
public interface MaterialLibraryMapper
{
    /**
     * 查询素材库
     * 
     * @param conditions 素材库主键
     * @return 素材库
     */
    public MaterialLibrary selectMaterialLibraryById(Map<String,Object> conditions);

    /**
     * 查询素材库列表
     * 
     * @param conditions 素材库
     * @return 素材库集合
     */
    public List<MaterialLibrary> selectMaterialLibraryList(Map<String,Object> conditions);

    /**
     * 新增素材库
     * 
     * @param materialLibrary 素材库
     * @return 结果
     */
    public int insertMaterialLibrary(MaterialLibrary materialLibrary);

    /**
     * 修改素材库
     * 
     * @param materialLibrary 素材库
     * @return 结果
     */
    public int updateMaterialLibrary(MaterialLibrary materialLibrary);

    /**
     * 批量删除素材库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialLibraryByIds(Long[] ids);
}
