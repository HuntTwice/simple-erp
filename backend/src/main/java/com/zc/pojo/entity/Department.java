package com.zc.pojo.entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    Integer id;

    String name;

    String address;

    LocalDateTime createTime;

    LocalDateTime updateTime;


    Integer createUser;

    Integer updateUser;
}
