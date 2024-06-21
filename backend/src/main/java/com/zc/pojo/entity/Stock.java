package com.zc.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock implements Serializable {
    private Integer id;
    private Integer supplierId;
    private Integer goodsId;
    private String payType;

    private LocalDateTime time;
    private Integer userId;
    private Integer num;
    private Double price;
    private Double totalPrice;
    private String comment;
};
