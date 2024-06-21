package com.zc.pojo.vo;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品销售
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleVO implements Serializable {


    /**
     * ID
     */
    @Alias("序号")
    private Integer id;
    /**
     * 客户ID
     */
    @Alias("客户ID")
    private Integer customerId;
    /**
     * 商品ID
     */
    @Alias("商品ID")
    private Integer goodsId;
    /**
     * 支付类型
     */
    @Alias("支付类型")
    private String payType;
    /**
     * 销售时间
     */
    @Alias("销售时间")
    private LocalDateTime time;
    /**
     * 操作人
     */
    @Alias("操作人")
    private String userId;
    /**
     * 销售价格
     */
    @Alias("销售价格")
    private Double price;
    /**
     * 销售数量
     */
    @Alias("销售数量")
    private Integer num;
    /**
     * 商品规格
     */
    @Alias("商品规格")
    private String unit;
    /**
     * 备注
     */
    @Alias("备注")
    private String comment;
    private String customerName;
    private String goodsName;

    private Double totalPrice;
    private String operator;

    private Integer totalInventory;



}
