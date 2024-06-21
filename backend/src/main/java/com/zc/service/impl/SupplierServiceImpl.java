package com.zc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.result.PageResult;
import com.zc.mapper.SupplierMapper;
import com.zc.pojo.dto.SupplierDTO;
import com.zc.pojo.entity.Supplier;
import com.zc.pojo.vo.UserQueryVO;
import com.zc.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public void add(Supplier supplier) {
        supplierMapper.add(supplier);
    }

    @Override
    public void deleteById(Integer id) {
        supplierMapper.deleteById(id);
    }

    @Override
    public void update(Supplier supplier) {
        supplierMapper.updateById(supplier);
    }

    @Override
    public PageResult pageSelect(SupplierDTO supplierDTO, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        Page<Supplier> page = supplierMapper.pageQuery(supplierDTO);
        long total = page.getTotal();
        List<Supplier> records = page.getResult();
        return new PageResult<>(total,records);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            supplierMapper.deleteById(id);
        }
    }

    @Override
    public List<Supplier> getAll() {
        return supplierMapper.getAll();
    }
}
