package com.edu.common.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.edu.common.core.domain.entity.Category;
import com.edu.common.core.domain.entity.SysDept;
import com.edu.common.core.domain.entity.SysMenu;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Treeselect树结构实体类
 *
 * @author edu
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(Category category)
    {
        this.id = category.getUuid();
        this.label = category.getTitle();
        this.children = category.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
