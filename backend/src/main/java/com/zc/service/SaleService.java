package com.zc.service;


import com.zc.common.result.PageResult;
import com.zc.pojo.dto.SaleDTO;
import com.zc.pojo.entity.Sale;
import com.zc.pojo.vo.SaleVO;

import java.util.List;

public interface SaleService {



    //新增部门记录
    void add(Sale sale);

    //通过id删除部门信息
    void deleteById(Integer id);



    //分页查询
    PageResult pageSelect(SaleDTO saleDTO ,Integer pageNum, Integer pageSize);



    //批量删除
    void deleteBatch(List<Integer> ids);

    List<SaleVO> getAll();
}
