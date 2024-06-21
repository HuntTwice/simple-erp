package com.zc.pojo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockBack {
    /** ID */
    private Integer id;
    /** 供应商ID */
    private Integer supplierId;
    /** 商品ID */
    private Integer goodsId;
    /** 支付类型 */
    private String payType;
    /** 退货时间 */
    private LocalDateTime time;
    /** 操作人ID */
    private Integer user;
    /** 退货数量 */
    private Integer num;
    /** 商品规格 */
    private String unit;
    /** 退货价格 */
    private Double price;
    /** 退货总价格 */
    private Double totalPrice;
    /** 备注 */
    private String comment;


}
