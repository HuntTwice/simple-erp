package com.zc.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationLog {

    /**
     * ID
     */
    private Integer id;

    //操作的类型
    private String operation;

    //操作的业务模块
    private String title;

    //操作对象
    private String target;

    //操作结果
    private String msg;

    //操作人的用户名
    private String username;

    //操作人的姓名
    private String name;


    //ip地址
    private String ip;


    //时间
    private LocalDateTime time;
}
