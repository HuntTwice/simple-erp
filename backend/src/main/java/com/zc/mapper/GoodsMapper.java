package com.zc.mapper;

import com.github.pagehelper.Page;
import com.zc.common.anno.AutoFill;
import com.zc.common.enums.OperationType;
import com.zc.pojo.dto.GoodsDTO;
import com.zc.pojo.entity.Goods;
import com.zc.pojo.vo.GoodsVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper {



    //通过id删除供应商信息
    @Delete("delete from goods where id = #{id}")
    int deleteById(Integer id);

    //根据id更新供应商信息
    @AutoFill(OperationType.UPDATE)
    int updateById(Goods goods);


    //新增供应商
    @AutoFill(OperationType.INSERT)
    int add(Goods goods);

    //通过id查询供应商
    @Select("select * from goods where id = #{id}")
    Goods getById(Integer id);

    //条件分页查询
    Page<GoodsVO> pageQuery(GoodsDTO goodsDTO);


    //查询所有
    @Select("select * from goods")
    List<GoodsVO> getAll();


    //查询库存的商品数
    @Select("select count(*) from goods")
    Integer getGoodsNum();
}
