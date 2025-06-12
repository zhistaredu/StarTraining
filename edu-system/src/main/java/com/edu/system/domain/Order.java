package com.edu.system.domain;

import java.math.BigDecimal;

import lombok.Data;
import com.edu.common.core.domain.BaseEntity;

/**
 * 订单对象 order
 * 
 * @author chuanzhiedu
 * @date 2024-04-01
 */
@Data
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 递增主键 */
    private Long id;

    /** 事件英文名 */
    private String syncAction;

    /** 用户购买第三方企业应用的suiteId */
    private String suiteId;

    /** 用户购买第三方企业应用的suiteKey */
    private String suiteKey;

    /** 购买企业的corpId */
    private String corpId;

    /** 商品码 */
    private String goodsCode;

    /** 规格码 */
    private String itemCode;

    /** 规格支持最大使用人数 */
    private Integer maxOfPeople;

    /** 规格支持最小使用人数 */
    private Integer minOfPeople;

    /** 订单id */
    private String orderId;

    /** 支付时间 */
    private Integer paidTime;

    /** 服务结束时间 */
    private Integer serviceStopTime;

    /** 实际支付价格（单位：分） */
    private Integer payFee;

    /** 订单创建来源，若值为TIANYUAN，表示来自天元系统平台 */
    private String orderCreateSource;

    /** 名义票面费用（单位：分），现与payFee值相等 */
    private Integer nominalPayFee;

    /** 折扣减免费用（单位：分），现值为0 */
    private Integer discountFee;

    /** 折扣，现值为1.00 */
    private BigDecimal discount;

    /** 分销商企业corpId */
    private String distributorCorpId;

    /** 分销商企业名称 */
    private String distributorCorpName;

    /** 解决方案名称 */
    private String solutionPackageName;

    /** 商品类型：normal普通商品，image:OXM镜像商品 */
    private String articleType;

    /** 购买数量 */
    private Integer subQuantity;

    /** 服务开始时间（单位：毫秒） */
    private Integer serviceStartTime;

    /** 订单类型，BUY新购，RENEW续费，UPGRADE升级，RENEW_UPGRADE续费升配，RENEW_DEGRADE续费降配 */
    private String orderType;

    /** 订单状态，0未生效1已生效 */
    private Integer status;

    /** 订单创建时间 */
    private Integer orderTime;

    /** 订单平台来源 */
    private String leadsFrom;

    /** 购买类型。 */
    private Integer purchaseType;

    /** 事件原内容 */
    private String eventData;
}
