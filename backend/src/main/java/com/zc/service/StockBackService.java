package com.zc.service;


import com.zc.common.result.PageResult;
import com.zc.pojo.dto.StockBackDTO;
import com.zc.pojo.entity.StockBack;
import com.zc.pojo.vo.StockBackVO;

import java.util.List;

public interface StockBackService {



    //新增记录
    void add(StockBack stockBack);


    //分页查询
    PageResult pageSelect(StockBackDTO stockBackDTO ,Integer pageNum, Integer pageSize);





    List<StockBackVO> getAll();
}
