package com.zc.mapper;

import cn.hutool.log.Log;
import com.github.pagehelper.Page;
import com.zc.common.result.PageResult;
import com.zc.pojo.entity.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper {

    @Insert("insert into operation_log(operation, title, target, msg, username, `name`, ip, `time`)" +
            " VALUES (#{operation},#{title},#{target},#{msg},#{username},#{name},#{ip},#{time})")
    int add(OperationLog log);



    Page<OperationLog> pageQuery();

}
