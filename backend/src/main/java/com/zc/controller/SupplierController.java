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
import com.zc.pojo.dto.SupplierDTO;
import com.zc.pojo.entity.Supplier;
import com.zc.service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/suppliers")
@Api(tags = "供应商信息管理")
@BusinessTitle(titleType = TitleType.SUPPLIER)
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    //新增供应商
    @PostMapping
    @ApiOperation("创建新的供应商记录")
    @BusinessOperation(operationType = OperationType.INSERT)
    public Result add(@RequestBody Supplier supplier) {
        supplierService.add(supplier);
        return Result.success();
    }


    //删除供应商
    @DeleteMapping("/{id}")
    @ApiOperation("删除供应商")
    @BusinessOperation(operationType = OperationType.DELETE)
    public Result delete(@PathVariable Integer id) {
        supplierService.deleteById(id);
        return Result.success();
    }

    //批量删除供应商
    @DeleteMapping("/batch")
    @ApiOperation("批量删除供应商")
    @BusinessOperation(operationType = OperationType.DELETE_BATCH)
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        supplierService.deleteBatch(ids);
        return Result.success();
    }

    //修改供应商信息
    @PutMapping
    @ApiOperation("修改供应商信息")
    @BusinessOperation(operationType = OperationType.UPDATE)
    public Result update(@RequestBody Supplier supplier) {
        supplierService.update(supplier);
        return Result.success();
    }


    /**
     * 分页查询
     * @param supplierDTO 查询条件封装为对象
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("条件分页查询")
    public Result pageSelect(@RequestBody(required = false) SupplierDTO supplierDTO,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        PageResult pageResult = supplierService.pageSelect(supplierDTO, pageNum, pageSize);
        return Result.success(pageResult);
    }


    @GetMapping
    @ApiOperation("查询所有供应商信息")
    public Result<List<Supplier>> getAll(){
        List<Supplier> suppliers = supplierService.getAll();
        return Result.success(suppliers);
    }


    /**
     * 批量导入
     */
    @PostMapping("/import")
    @ApiOperation("批量导入")
    public Result excelImport(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Supplier> list = reader.readAll(Supplier.class);
        // 写入数据到数据库
        try {
            for (Supplier supplier : list) {
                supplierService.add(supplier);
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

        List<Supplier> list = supplierService.getAll();
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("供应商信息表", "UTF-8") + ".xlsx");

        //输出流，通过输出流将文件写回浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.flush();
        writer.close();
        outputStream.close();
    }
}
