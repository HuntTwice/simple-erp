package com.zc.mapper;

import com.github.pagehelper.Page;
import com.zc.pojo.dto.SaleDTO;
import com.zc.pojo.entity.Sale;
import com.zc.pojo.vo.SaleVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SaleMapper {


    //通过id删除销售信息
    @Delete("delete from sale where id = #{id}")
    int deleteById(Integer id);

    //根据id更新销售信息
    int updateById(Sale sale);


    //新增销售信息
    int add(Sale sale);

    //通过id查询销售信息
    @Select("select * from sale where id = #{id}")
    Sale getById(Integer id);

    //条件分页查询
    Page<SaleVO> pageQuery(SaleDTO saleDTO);


    //查询所有
    @Select("select * from sale")
    List<SaleVO> getAll();


    //查询所有的销售订单数
    @Select("select count(*) from sale")
    Integer countAll();


    //查询销售总额
    @Select("select sum(total_price) as totalSalePrice from sale")
    Double getTotalSalePrice();


    //查询近一周每天的交易总额
    /*
    * SELECT DATE_FORMAT(date, '%Y-%m-%d') AS sales_date, SUM(amount) AS total_sales
        FROM sales
        WHERE date BETWEEN CURDATE() - INTERVAL 1 WEEK AND CURDATE()
        GROUP BY sales_date;
* */
    @Select("SELECT * FROM sale WHERE time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY);")
    List<SaleVO> getByNearWeek();
}
