package com.zc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockVO {
    private Integer id;
    private Integer supplierId;
    private Integer goodsId;
    private String supplierName;
    private String goodsName;
    private String payType;
    private LocalDateTime time;
    private Integer userId; //操作的用户id
    private String operator; //操作人
    private Integer num; //进货数量
    private Integer totalInventory;
    private String unit;
    private Double price;
    private Double totalPrice;
    private String comment;
}
