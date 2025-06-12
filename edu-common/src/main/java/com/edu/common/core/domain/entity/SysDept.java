package com.edu.common.core.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edu.common.core.domain.BaseEntity;

/**
 * 部门表 sys_dept
 * 
 * @author edu
 */
public class SysDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部门ID */
    private String deptId;

    private String keyword;

    /** 父部门ID */
    private String parentId;

    private String deptIdPath;

    private String deptNamePath;

    /** 祖级列表 */
    private String ancestors;

    /** 部门名称 */
    private String deptName;

    /** 显示顺序 */
    private String orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 部门状态:0正常,1停用 */
    private Integer status;

    private Integer statusType;

    /** 删除标志（0代表存在 2代表删除） */
    private Integer isDelete;

    /** 父部门名称 */
    private String parentName;

    /** 公司ID */
    private String companyId;

    /** 公司name */
    private String companyName;

    /** 部门人数 */
    private Integer deptUserNum = 0;

    /** 是否有子部门 */
    private String isChildren;
    
    /** 子部门 */
    private List<SysDept> children = new ArrayList<SysDept>();

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }
    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptIdPath()
    {
        return deptIdPath;
    }

    public void setDeptIdPath(String deptIdPath)
    {
        this.deptIdPath = deptIdPath;
    }

    public String getDeptNamePath()
    {
        return deptNamePath;
    }

    public void setDeptNamePath(String deptNamePath)
    {
        this.deptNamePath = deptNamePath;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    @NotBlank(message = "显示顺序不能为空")
    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatusType()
    {
        return statusType;
    }

    public void setStatusType(Integer statusType)
    {
        this.statusType = statusType;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

    public Integer getDeptUserNum()
    {
        return deptUserNum;
    }

    public void setDeptUserNum(Integer deptUserNum)
    {
        this.deptUserNum = deptUserNum;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getIsChildren()
    {
        return isChildren;
    }

    public void setIsChildren(String isChildren)
    {
        this.isChildren = isChildren;
    }

    public List<SysDept> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysDept> children)
    {
        this.children = children;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("deptName", getDeptName())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("status", getStatus())
            .append("isDelete", getIsDelete())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
