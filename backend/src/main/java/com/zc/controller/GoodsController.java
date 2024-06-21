package com.zc.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zc.common.anno.BusinessOperation;
import com.zc.common.anno.BusinessTitle;
import com.zc.common.enums.OperationType;
import com.zc.common.enums.ResultCodeEnum;
import com.zc.common.enums.TitleType;
import com.zc.common.result.PageResult;
import com.zc.common.result.Result;
import com.zc.pojo.dto.GoodsDTO;
import com.zc.pojo.entity.Goods;
import com.zc.pojo.vo.GoodsVO;
import com.zc.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/goods")
@Api(tags = "商品信息管理")
@BusinessTitle(titleType = TitleType.GOODS)
public class GoodsController {

    @Autowired
    private GoodsService goodsService;



    //新增商品
    @PostMapping
    @ApiOperation("新增商品")
    @BusinessOperation(operationType = OperationType.INSERT)
    public Result add(@RequestBody Goods goods) {
        goodsService.add(goods);
        return Result.success();
    }


    //删除
    @DeleteMapping("/{id}")
    @ApiOperation("删除商品")
    @BusinessOperation(operationType = OperationType.DELETE)
    public Result delete(@PathVariable Integer id) {
        goodsService.deleteById(id);
        return Result.success();
    }


    //批量删除
    @DeleteMapping("/batch")
    @ApiOperation("批量删除商品")
    @BusinessOperation(operationType = OperationType.DELETE_BATCH)
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        goodsService.deleteBatch(ids);
        return Result.success();
    }

    // 修改商品信息
    @PutMapping
    @ApiOperation("修改商品信息")
    @BusinessOperation(operationType = OperationType.UPDATE)
    public Result update(@RequestBody Goods goods) {
        goodsService.update(goods);
        return Result.success();
    }


    /**
     * 分页查询
     * @param goodsDTO 查询条件封装为对象
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result pageSelect(@RequestBody(required = false) GoodsDTO goodsDTO,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        PageResult pageResult = goodsService.pageSelect(goodsDTO, pageNum, pageSize);
        return Result.success(pageResult);
    }


    //查询所有商品信息
    @ApiOperation("查询所有商品信息")
    @GetMapping
    public Result getAll(GoodsDTO goodsDTO){
        List<GoodsVO> goodsVOS = goodsService.getAll(goodsDTO);
        return Result.success(goodsVOS);
    }

    /**
     * 批量导入
     */
    @PostMapping("/import")
    @ApiOperation("批量导入")
    public Result excelImport(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<GoodsVO> GoodsVOs = reader.readAll(GoodsVO.class);
        ArrayList<Goods> goodsList = new ArrayList<>();
        for (GoodsVO goodsVO : GoodsVOs) {
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsVO,goods);
            goodsList.add(goods);
        }
        // 写入数据到数据库
        try {
            for (Goods goods : goodsList) {
                goodsService.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultCodeEnum.DATA_IMPORT_ERROR);
        }
        return Result.success();
    }



    /**
     * 批量导出数据
     */
    @ApiOperation("批量导出")
    @GetMapping("/export")
    public void excelExport(HttpServletResponse response) throws IOException {

        System.out.println("访问export");
        ExcelWriter writer = ExcelUtil.getWriter(true);

        List<GoodsVO> list = goodsService.getAll(new GoodsDTO());
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("商品信息表", "UTF-8") + ".xlsx");

        //输出流，通过输出流将文件写回浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.flush();
        writer.close();
        outputStream.close();
    }
}
