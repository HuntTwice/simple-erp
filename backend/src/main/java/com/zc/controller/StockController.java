package com.zc.controller;

import com.zc.common.result.PageResult;
import com.zc.common.result.Result;
import com.zc.pojo.dto.StockDTO;
import com.zc.pojo.entity.Stock;
import com.zc.pojo.vo.StockVO;
import com.zc.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/stocks")
@Api(tags = "商品信息管理")
public class StockController {

    @Autowired
    private StockService stockService;



    //新增商品信息
    @PostMapping
    @ApiOperation("创建新的商品记录")
    public Result add(@RequestBody Stock stock) {
        LocalDateTime time = stock.getTime();
        log.info("接收到的时间:{}",time.toString());
        stockService.add(stock);
        return Result.success();
    }


    //删除
    @DeleteMapping("/{id}")
    @ApiOperation("删除商品的信息")
    public Result delete(@PathVariable Integer id) {
        stockService.deleteById(id);
        return Result.success();
    }


    //批量删除
    @DeleteMapping("/batch")
    @ApiOperation("批量删除商品信息")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        stockService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改商品信息
     * @param stock
     * @return
     */
    @PutMapping
    @ApiOperation("修改商品信息")
    public Result update(@RequestBody Stock stock) {
        stockService.update(stock);
        return Result.success();
    }


    /**
     * 分页查询
     * @param stockDTO 查询条件封装为对象
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result pageSelect(@RequestBody(required = false) StockDTO stockDTO,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        PageResult pageResult = stockService.pageSelect(stockDTO, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping
    public Result getAll(){
        List<StockVO> stockVOS = stockService.getAll();
        return Result.success(stockVOS);
    }






}
