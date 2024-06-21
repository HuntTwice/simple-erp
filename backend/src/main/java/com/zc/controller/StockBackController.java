package com.zc.controller;

import com.zc.common.result.PageResult;
import com.zc.common.result.Result;
import com.zc.pojo.dto.StockBackDTO;
import com.zc.pojo.entity.StockBack;
import com.zc.pojo.vo.StockBackVO;
import com.zc.service.StockBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/stockBacks")
@Api(tags = "商品信息管理")
public class StockBackController {

    @Autowired
    private StockBackService stockBackService;



    //新增商品信息
    @PostMapping
    @ApiOperation("创建新的商品记录")
    public Result add(@RequestBody StockBack stockBack) {
        stockBackService.add(stockBack);
        return Result.success();
    }

    /**
     * 分页查询
     * @param stockBackDTO 查询条件封装为对象
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result pageSelect(@RequestBody(required = false) StockBackDTO stockBackDTO,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        PageResult pageResult = stockBackService.pageSelect(stockBackDTO, pageNum, pageSize);
        return Result.success(pageResult);
    }


    @GetMapping
    public Result getAll(){
        List<StockBackVO> stockBackVOS = stockBackService.getAll();
        return Result.success(stockBackVOS);
    }

}
