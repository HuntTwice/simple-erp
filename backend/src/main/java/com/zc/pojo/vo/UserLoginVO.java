package com.zc.pojo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户登录成功返回给前端的数据格式")
public class UserLoginVO {
    @ApiModelProperty("主键值")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户权限")
    private String role;

    @ApiModelProperty("用户头像地址")
    private String avatar;

    @ApiModelProperty("昵称")
    private String name;
    @ApiModelProperty("token")
    private String token;


}
