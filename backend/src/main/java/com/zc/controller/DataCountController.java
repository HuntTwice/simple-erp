package com.zc.controller;


import com.zc.common.result.Result;
import com.zc.mapper.GoodsMapper;
import com.zc.mapper.SaleMapper;
import com.zc.mapper.StockMapper;
import com.zc.pojo.entity.Goods;
import com.zc.pojo.entity.Store;
import com.zc.pojo.entity.TotalSalePriceCurrentDay;
import com.zc.pojo.vo.GoodsVO;
import com.zc.pojo.vo.SaleVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/count")
@Api(tags = "数据统计")
public class DataCountController {
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @GetMapping
    public Result getData() {
        Integer stockOrdersNum = stockMapper.countAll();
        Integer saleOrdersNum = saleMapper.countAll();
        Double totalSalePrice = saleMapper.getTotalSalePrice();
        Integer goodsNum = goodsMapper.getGoodsNum();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("stockOrdersNum", stockOrdersNum);
        map.put("saleOrdersNum", saleOrdersNum);
        map.put("totalSalePrice", totalSalePrice);
        map.put("goodsNum", goodsNum);
        return Result.success(map);
    }


    @GetMapping("/weekSale")
    public Result getWeekSale() {

        //获取所有的销售订单
        List<SaleVO> saleList = saleMapper.getAll();

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 创建一个列表来存储过去七天的日期
        List<LocalDate> pastSevenDays = new ArrayList<>();

        // 添加过去七天的日期到列表中
        for (int i = 0; i < 7; i++)
            pastSevenDays.add(currentDate.minusDays(i));
        Collections.sort(pastSevenDays);
        pastSevenDays.stream().sorted();
        ArrayList<TotalSalePriceCurrentDay> daySalePriceList = new ArrayList<>();
        for (LocalDate date : pastSevenDays) {
            Double totalSalePriceCurrentDay = saleList.stream()
                    .filter(saleVO -> saleVO.getTime().toLocalDate().equals(date) ) //筛选日期
                    .map(SaleVO::getTotalPrice)
                    .reduce(Double::sum).orElse(0D);
            TotalSalePriceCurrentDay daySalePrice = TotalSalePriceCurrentDay.builder()
                    .date(date)
                    .price(totalSalePriceCurrentDay)
                    .build();
            daySalePriceList.add(daySalePrice);
        }
        return Result.success(daySalePriceList);
    }



    @GetMapping("/store")
    public Result storeCount(){
        List<GoodsVO> allGoods = goodsMapper.getAll();
        ArrayList<Store> storeList = new ArrayList<>();

        List<String> nameList = allGoods.stream().map(GoodsVO::getName).collect(Collectors.toList());
        for (String name : nameList) {
            Integer num = allGoods.stream().filter(goodsVO -> goodsVO.getName().equals(name)).map(GoodsVO::getNum).reduce(Integer::sum).orElse(0);
            Store store = new Store(name, num);
            storeList.add(store);
        }
        return Result.success(storeList);
    }
//    //获取采购订单数目
//    public Result getStockOrdersNum(){
//        Integer stockOrdersNum = stockMapper.countAll();
//        return Result.success(stockOrdersNum);
//    }
//
//
//
//    //获取销售订单数
//    public Result getSaleOrdersNum(){
//        Integer saleOrdersNum = saleMapper.countAll();
//        return Result.success(saleOrdersNum);
//    }
}
