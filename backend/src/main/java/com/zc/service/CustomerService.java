package com.zc.service;


import com.zc.common.result.PageResult;
import com.zc.pojo.dto.CustomerDTO;
import com.zc.pojo.entity.Customer;

import java.util.List;

public interface CustomerService {



    //新增客户记录
    void add(Customer customer);

    //通过id删除客户信息
    void deleteById(Integer id);

    //通过id修改客户信息
    void update(Customer customer);


    //分页查询
    PageResult pageSelect(CustomerDTO customerDTO, Integer pageNum, Integer pageSize);



    //批量删除
    void deleteBatch(List<Integer> ids);

    List<Customer> getAll();
}
