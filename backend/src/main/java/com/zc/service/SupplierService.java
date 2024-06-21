package com.zc.service;


import com.zc.common.result.PageResult;
import com.zc.pojo.dto.SupplierDTO;
import com.zc.pojo.entity.Supplier;


import java.util.List;

public interface SupplierService {



    //新增供应商记录
    void add(Supplier supplier);

    //通过id删除供应商信息
    void deleteById(Integer id);

    //通过id修改供应商信息
    void update(Supplier supplier);


    //分页查询
    PageResult pageSelect(SupplierDTO supplierDTO, Integer pageNum, Integer pageSize);



    //批量删除
    void deleteBatch(List<Integer> ids);

    List<Supplier> getAll();
}
