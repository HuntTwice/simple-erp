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
import com.zc.pojo.dto.CustomerDTO;
import com.zc.pojo.entity.Customer;
import com.zc.pojo.entity.Supplier;
import com.zc.service.CustomerService;
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
@RequestMapping("/customers")
@Api(tags = "客户信息管理")
@BusinessTitle(titleType = TitleType.CUSTOMER)
public class CustomerController {

    @Autowired
    private CustomerService customerService;



    //新增客户
    @PostMapping
    @ApiOperation("新增客户")
    @BusinessOperation(operationType = OperationType.INSERT)
    public Result add(@RequestBody Customer customer) {
        customerService.add(customer);
        return Result.success();
    }


    //删除客户
   @DeleteMapping("/{id}")
    @ApiOperation("删除客户")
    @BusinessOperation(operationType = OperationType.DELETE)
    public Result delete(@PathVariable Integer id) {
        customerService.deleteById(id);
        return Result.success();
    }

    //批量删除客户
    @DeleteMapping("/batch")
    @ApiOperation("批量删除客户")
    @BusinessOperation(operationType = OperationType.DELETE_BATCH)
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        customerService.deleteBatch(ids);
        return Result.success();
    }


    // 修改客户信息
    @PutMapping
    @ApiOperation("修改客户信息")
    @BusinessOperation(operationType = OperationType.UPDATE)
    public Result update(@RequestBody Customer customer) {
        customerService.update(customer);
        return Result.success();
    }


    /**
     * 分页查询
     * @param customerDTO 查询条件封装为对象
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result pageSelect(@RequestBody(required = false) CustomerDTO customerDTO,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        PageResult pageResult = customerService.pageSelect(customerDTO, pageNum, pageSize);
        return Result.success(pageResult);
    }

    //查询所有客户信息
    @GetMapping
    @ApiOperation("查询所有客户信息")
    public Result<List<Customer>> getAll(){
        List<Customer> customers = customerService.getAll();
        return Result.success(customers);
    }


    /**
     * 批量导入
     */
    @PostMapping("/import")
    @ApiOperation("批量导入")
    public Result excelImport(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Customer> list = reader.readAll(Customer.class);
        // 写入数据到数据库
        try {
            for (Customer customer : list) {
                customerService.add(customer);
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

        List<Customer> list = customerService.getAll();
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("客户信息表", "UTF-8") + ".xlsx");

        //输出流，通过输出流将文件写回浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.flush();
        writer.close();
        outputStream.close();
    }
}
