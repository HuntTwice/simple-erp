package com.zc.mapper;

import com.github.pagehelper.Page;
import com.zc.common.anno.AutoFill;
import com.zc.common.enums.OperationType;
import com.zc.pojo.entity.Department;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapper {


    //通过id删除部门信息
    @Delete("delete from department where id = #{id}")
    int deleteById(Integer id);

    //根据id更新部门信息
    @AutoFill(OperationType.UPDATE)
    int updateById(Department department);


    //新增部门
    @AutoFill(OperationType.INSERT)
    int add(Department department);

    //通过id查询部门
    @Select("select * from department where id = #{id}")
    Department getById(Integer id);

    //条件分页查询
    Page<Department> pageQuery();


    //查询所有
    @Select("select * from department")
    List<Department> getAll();
}
