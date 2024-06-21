package com.zc.mapper;

import com.github.pagehelper.Page;
import com.zc.common.anno.AutoFill;
import com.zc.common.enums.OperationType;
import com.zc.pojo.dto.SupplierDTO;
import com.zc.pojo.entity.Supplier;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SupplierMapper {


    //通过id删除供应商信息
    @Delete("delete from supplier where id = #{id}")
    int deleteById(Integer id);

    //根据id更新供应商信息
    @AutoFill(OperationType.UPDATE)
    int updateById(Supplier supplier);


    //新增供应商
    @AutoFill(OperationType.INSERT)
    int add(Supplier supplier);

    //通过id查询供应商
    @Select("select * from supplier where id = #{id}")
    Supplier getById(Integer id);

    //条件分页查询
    Page<Supplier> pageQuery(SupplierDTO supplierDTO);


    //查询所有
    @Select("select * from supplier")
    List<Supplier> getAll();
}
