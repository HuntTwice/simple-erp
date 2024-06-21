package com.zc.mapper;

import com.github.pagehelper.Page;
import com.zc.common.anno.AutoFill;
import com.zc.common.enums.OperationType;
import com.zc.pojo.dto.StockDTO;
import com.zc.pojo.entity.Stock;
import com.zc.pojo.vo.StockVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockMapper {


    //通过id删除供应商信息
    @Delete("delete from stock where id = #{id}")
    int deleteById(Integer id);

    //根据id更新供应商信息
    int updateById(Stock stock);


    //新增供应商

    int add(Stock stock);

    //通过id查询供应商
    @Select("select * from stock where id = #{id}")
    Stock getById(Integer id);

    //条件分页查询
    Page<StockVO> pageQuery(StockDTO stockDTO);


    //查询所有采购订单信息
    @Select("select * from stock")
    List<StockVO> getAll();

    //查询采购订单数
    @Select("select count(*) from stock")
    Integer countAll();
}
