package com.zc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.result.PageResult;
import com.zc.mapper.OperationLogMapper;
import com.zc.pojo.entity.OperationLog;
import com.zc.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public PageResult pageQuery(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<OperationLog> page = operationLogMapper.pageQuery();
        long total = page.getTotal();
        List<OperationLog> records = page.getResult();
        return new PageResult(total,records);

    }
}
