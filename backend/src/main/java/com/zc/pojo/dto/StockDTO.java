package com.zc.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private Integer id;
    private Integer supplierId;
    private Integer goodsId;
    private String goodsName;
    private String supplierName;
    private String payType;
    private LocalDateTime time;
    private Integer user;
    private Integer num;
    private String unit;
    private Double price;
    private Double totalPrice;
    private String comment;
    private String operator;
};
