package com.zc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.result.PageResult;
import com.zc.mapper.DepartmentMapper;
import com.zc.pojo.entity.Department;
import com.zc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void add(Department department) {
        departmentMapper.add(department);
    }

    @Override
    public void deleteById(Integer id) {
        departmentMapper.deleteById(id);
    }

    @Override
    public void update(Department department) {
        departmentMapper.updateById(department);
    }

    @Override
    public PageResult pageSelect(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        Page<Department> page = departmentMapper.pageQuery();
        long total = page.getTotal();
        List<Department> records = page.getResult();
        return new PageResult<>(total,records);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            departmentMapper.deleteById(id);
        }
    }

    @Override
    public List<Department> getAll() {
        return departmentMapper.getAll();
    }
}
