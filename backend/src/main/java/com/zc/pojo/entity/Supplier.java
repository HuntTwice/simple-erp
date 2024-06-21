package com.zc.pojo.entity;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    @Alias("id")
    private Integer id;

    @Alias("供应商名称")
    private String name;

    @Alias("供应商地址")
    private String address;

    @Alias("供应商电话")
    private String phone;

    @Alias("供应商邮箱")
    private String email;

    @Alias("邮编")
    private String zipCode;

    @Alias("联系人")
    private String user;

    @Alias("联系人电话")
    private String userTel;

    @Alias("开户银行")
    private String bank;

    @Alias("开户行账号")
    private String bankCard;

    @Alias("状态")
    private Boolean status;

    @Alias("创建时间")
    private LocalDateTime createTime;

    @Alias("修改时间")
    private LocalDateTime updateTime;

    @Alias("创建人")
    private Integer createUser;

    @Alias("修改人")
    private Integer updateUser;
}
