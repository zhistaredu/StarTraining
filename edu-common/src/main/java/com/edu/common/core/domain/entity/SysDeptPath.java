package com.edu.common.core.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edu.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门路径表对象 sys_dept_path
 * 
 * @author chuanzhiedu
 * @date 2023-01-18
 */
public class SysDeptPath extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 递增ID */
    private Long id;

    /** 部门ID */
    private String deptId;

    /** 部门名称 */
    private String deptName;

    private String parentId;

    /** 部门ID父路径 */
    private String deptIdParentPath;

    /** 部门姓名父路径 */
    private String deptNameParentPath;

    /** 是否是外部部门（0外部1外部） */
    private Integer isIn;

    /** 同步状态（0未更新，1已更新） */
    private Integer syncStatus;

    /** 来源（platform、dingDingIsv、weixin） */
    private String source;

    /** 0未发布1已发布 */
    private Integer status;

    private Integer statusType;

    /** 0未删除1已删除 */
    private Integer isDelete;

    /** 创建者userId */
    private String createUserId;

    /** 创建者用户姓名 */
    private String createUserName;

    /** 更新者userId */
    private String updateUserId;

    /** 更新者用户姓名 */
    private String updateUserName;

    /** 公司ID */
    private String companyId;

    /** 公司名称 */
    private String companyName;

    private List<String> parentPath;

    /** 子部门 */
    private List<SysDeptPath> children = new ArrayList<SysDeptPath>();

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setParentPath(List<String> parentPath)
    {
        this.parentPath = parentPath;
    }

    public List<String> getParentPath()
    {
        return parentPath;
    }
    public void setDeptId(String deptId) 
    {
        this.deptId = deptId;
    }

    public String getDeptId() 
    {
        return deptId;
    }
    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }

    public List<SysDeptPath> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysDeptPath> children)
    {
        this.children = children;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setDeptIdParentPath(String deptIdParentPath) 
    {
        this.deptIdParentPath = deptIdParentPath;
    }

    public String getDeptIdParentPath() 
    {
        return deptIdParentPath;
    }
    public void setDeptNameParentPath(String deptNameParentPath) 
    {
        this.deptNameParentPath = deptNameParentPath;
    }

    public String getDeptNameParentPath() 
    {
        return deptNameParentPath;
    }
    public void setIsIn(Integer isIn) 
    {
        this.isIn = isIn;
    }

    public Integer getIsIn() 
    {
        return isIn;
    }
    public void setSyncStatus(Integer syncStatus) 
    {
        this.syncStatus = syncStatus;
    }

    public Integer getSyncStatus() 
    {
        return syncStatus;
    }
    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getSource() 
    {
        return source;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setStatusType(Integer statusType)
    {
        this.statusType = statusType;
    }

    public Integer getStatusType()
    {
        return statusType;
    }
    public void setIsDelete(Integer isDelete) 
    {
        this.isDelete = isDelete;
    }

    public Integer getIsDelete() 
    {
        return isDelete;
    }
    public void setCreateUserId(String createUserId) 
    {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() 
    {
        return createUserId;
    }
    public void setCreateUserName(String createUserName) 
    {
        this.createUserName = createUserName;
    }

    public String getCreateUserName() 
    {
        return createUserName;
    }
    public void setUpdateUserId(String updateUserId) 
    {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserId() 
    {
        return updateUserId;
    }
    public void setUpdateUserName(String updateUserName) 
    {
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserName() 
    {
        return updateUserName;
    }
    public void setCompanyId(String companyId) 
    {
        this.companyId = companyId;
    }

    public String getCompanyId() 
    {
        return companyId;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("deptIdParentPath", getDeptIdParentPath())
            .append("deptNameParentPath", getDeptNameParentPath())
            .append("isIn", getIsIn())
            .append("syncStatus", getSyncStatus())
            .append("source", getSource())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .append("isDelete", getIsDelete())
            .append("createUserId", getCreateUserId())
            .append("createUserName", getCreateUserName())
            .append("updateUserId", getUpdateUserId())
            .append("updateUserName", getUpdateUserName())
            .append("companyId", getCompanyId())
            .append("companyName", getCompanyName())
            .toString();
    }
}
