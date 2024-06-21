package com.zc.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(description = "增加、修改接口请求时的参数封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;

    //    员工账号的用户名
    private String username;

    //    密码
    private String password;

    //    账号状态
    private Boolean status;

    //    员工角色
    private String role;

    //员工所属部门id
    private Integer departmentId;

    //    昵称
    private String name;

    //    头像,存放url地址
    private String avatar;

    //年龄
    private Integer age;
    // 性别
    private String sex;


    //    手机号
    private String phone;


    //    电子邮箱
    private String email;

    // 地址
    private String address;
}
