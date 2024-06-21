package com.zc.service;


import com.zc.common.result.PageResult;

import com.zc.pojo.entity.Department;

import java.util.List;

public interface DepartmentService {



    //新增部门记录
    void add(Department department);

    //通过id删除部门信息
    void deleteById(Integer id);

    //通过id修改部门信息
    void update(Department department);


    //分页查询
    PageResult pageSelect(Integer pageNum, Integer pageSize);



    //批量删除
    void deleteBatch(List<Integer> ids);

    List<Department> getAll();
}
