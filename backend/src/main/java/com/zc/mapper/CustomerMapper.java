package com.zc.mapper;

import com.github.pagehelper.Page;
import com.zc.common.anno.AutoFill;
import com.zc.common.enums.OperationType;
import com.zc.pojo.dto.CustomerDTO;
import com.zc.pojo.entity.Customer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper {


    //通过id删除客户信息
    @Delete("delete from customer where id = #{id}")
    int deleteById(Integer id);

    //根据id更新客户信息
    @AutoFill(OperationType.UPDATE)
    int updateById(Customer customer);


    //新增客户
    @AutoFill(OperationType.INSERT)
    int add(Customer customer);

    //通过id查询客户
    @Select("select * from customer where id = #{id}")
    Customer getById(Integer id);

    //条件分页查询
    Page<Customer> pageQuery(CustomerDTO customerDTO);


    //查询所有
    @Select("select * from customer")
    List<Customer> getAll();
}
