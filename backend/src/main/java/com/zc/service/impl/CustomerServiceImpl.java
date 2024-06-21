package com.zc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.result.PageResult;
import com.zc.mapper.CustomerMapper;
import com.zc.pojo.dto.CustomerDTO;
import com.zc.pojo.entity.Customer;
import com.zc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void add(Customer customer) {
        customerMapper.add(customer);
    }

    @Override
    public void deleteById(Integer id) {
        customerMapper.deleteById(id);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateById(customer);
    }

    @Override
    public PageResult pageSelect(CustomerDTO customerDTO, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        Page<Customer> page = customerMapper.pageQuery(customerDTO);
        long total = page.getTotal();
        List<Customer> records = page.getResult();
        return new PageResult<>(total,records);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            customerMapper.deleteById(id);
        }
    }

    @Override
    public List<Customer> getAll() {
        return customerMapper.getAll();
    }
}
