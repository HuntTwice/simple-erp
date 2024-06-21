package com.zc.service;

import com.zc.common.result.PageResult;

public interface LogService {

    PageResult pageQuery(Integer pageNum,Integer pageSize);
}
