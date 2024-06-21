package com.zc.pojo.dto;

import com.zc.common.result.PageResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO implements Serializable {

    private Integer id;

    private String name;

    private String supplierId;

    private String producer;

    private String productNo;

    private String approveNo;

    private Boolean status;

    private String supplierName;


}

