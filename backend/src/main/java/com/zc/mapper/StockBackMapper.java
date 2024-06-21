package com.zc.mapper;

import com.github.pagehelper.Page;
import com.zc.pojo.dto.StockBackDTO;
import com.zc.pojo.entity.StockBack;
import com.zc.pojo.vo.StockBackVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockBackMapper {

    //新增退货信息
    int add(StockBack stockBack);

    //通过id查询退货信息
    @Select("select * from stock_back where id = #{id}")
    StockBack getById(Integer id);

    //条件分页查询
    Page<StockBackVO> pageQuery(StockBackDTO stockBackDTO);


    //查询所有
    @Select("select * from stock_back")
    List<StockBackVO> getAll();
}
