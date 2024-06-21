package com.zc.service;


import com.zc.common.result.PageResult;
import com.zc.pojo.dto.StockDTO;
import com.zc.pojo.entity.Stock;
import com.zc.pojo.vo.StockVO;

import java.util.List;

public interface StockService {



    //新增部门记录
    void add(Stock stock);

    //通过id删除部门信息
    void deleteById(Integer id);

    //通过id修改部门信息
    void update(Stock stock);


    //分页查询
    PageResult pageSelect(StockDTO stockDTO ,Integer pageNum, Integer pageSize);



    //批量删除
    void deleteBatch(List<Integer> ids);

    List<StockVO> getAll();
}
