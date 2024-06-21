package com.zc.pojo.vo;

import cn.hutool.core.annotation.Alias;
import com.zc.pojo.entity.Goods;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVO extends Goods {
    private Integer id;

    @Alias("商品名称")
    private String name;

    @Alias("供应商ID")
    private Integer supplierId;

    @Alias("供应商")
    private String supplierName;

    @Alias("商品产地")
    private String producer;

    @Alias("商品描述")
    private String description;

    @Alias("商品数量")
    private Integer num;

    @Alias("商品规格")
    private String unit;

    @Alias("商品图片")
    private String img;

    @Alias("生产批号")
    private String productNo;

    @Alias("批准文号")
    private String approveNo;

    @Alias("状态")
    private Boolean status;


}
