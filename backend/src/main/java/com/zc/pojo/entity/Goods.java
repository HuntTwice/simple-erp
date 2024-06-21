package com.zc.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (Goods)实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    private Integer id;

    private String name;

    private Integer supplierId;

    private String producer;

    private String description;

    private Integer num;

    private String unit;

    private String img;

    private String productNo;

    private String approveNo;

    private Boolean status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer createUser;

    private Integer updateUser;



}

