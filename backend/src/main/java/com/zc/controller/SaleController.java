package com.zc.controller;

import com.zc.common.anno.BusinessOperation;
import com.zc.common.anno.BusinessTitle;
import com.zc.common.enums.OperationType;
import com.zc.common.enums.TitleType;
import com.zc.common.result.PageResult;
import com.zc.common.result.Result;
import com.zc.pojo.dto.SaleDTO;
import com.zc.pojo.entity.Sale;
import com.zc.pojo.vo.SaleVO;
import com.zc.service.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/sales")
@Api(tags = "销售信息管理")
@BusinessTitle(titleType = TitleType.SALE)
public class SaleController {

    @Autowired
    private SaleService saleService;



    //新增商品信息
    @PostMapping
    @ApiOperation("销售商品")
    @BusinessOperation(operationType = OperationType.INSERT)
    public Result add(@RequestBody Sale sale) {
        saleService.add(sale);
        return Result.success();
    }


    //删除
    @DeleteMapping("/{id}")
    @ApiOperation("删除销售信息")
    @BusinessOperation(operationType = OperationType.DELETE)
    public Result delete(@PathVariable Integer id) {
        saleService.deleteById(id);
        return Result.success();
    }


    //批量删除
    @DeleteMapping("/batch")
    @ApiOperation("批量删除销售信息")
    @BusinessOperation(operationType = OperationType.DELETE_BATCH)
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        saleService.deleteBatch(ids);
        return Result.success();
    }




    /**
     * 分页查询
     * @param saleDTO 查询条件封装为对象
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result pageSelect(@RequestBody(required = false) SaleDTO saleDTO,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        PageResult pageResult = saleService.pageSelect(saleDTO, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping
    public Result getAll(){
        List<SaleVO> saleVOS = saleService.getAll();
        return Result.success(saleVOS);
    }


}
