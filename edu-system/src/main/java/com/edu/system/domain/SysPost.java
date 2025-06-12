package com.edu.system.domain;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 岗位信息对象 sys_post
 * 
 * @author chuanzhiedu
 * @date 2024-05-03
 */
@Data
public class SysPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 岗位ID */
    private Long id;

    /** 岗位编码 */
    private String postId;

    private String groupId;

    private String groupName;

    /** 岗位名称 */
    private String postName;

    private Integer userNum;

    /** 显示顺序 */
    private Long postSort;

    /** 岗位职责 */
    private String duty;

    /** 岗位要求 */
    private String demand;

    private Integer amount;

    /** 状态（0正常 1删除） */
    private Integer isDelete;

    /** 创建者 */
    private String createUserId;

    /** 更新者 */
    private String updateUserId;

    /** 公司id */
    private String companyId;
}
