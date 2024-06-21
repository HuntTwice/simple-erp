package com.zc.controller;

import com.zc.common.anno.BusinessOperation;
import com.zc.common.anno.BusinessTitle;
import com.zc.common.enums.OperationType;
import com.zc.common.enums.TitleType;
import com.zc.common.result.PageResult;
import com.zc.common.result.Result;
import com.zc.pojo.entity.Department;
import com.zc.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/departments")
@Api(tags = "部门信息管理")
@BusinessTitle(titleType = TitleType.DEPARTMENT)
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;



    //新增部门信息
    @PostMapping
    @ApiOperation("创建新的部门")
    @BusinessOperation(operationType = OperationType.INSERT)
    public Result add(@RequestBody Department department) {
        departmentService.add(department);
        return Result.success();
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除部门")
    @BusinessOperation(operationType = OperationType.DELETE)
    public Result delete(@PathVariable Integer id) {
        departmentService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除部门")
    @BusinessOperation(operationType = OperationType.DELETE_BATCH)
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        departmentService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改部门信息
     * @param department
     * @return
     */
    @PutMapping
    @ApiOperation("修改部门信息")
    @BusinessOperation(operationType = OperationType.UPDATE)
    public Result update(@RequestBody Department department) {
        departmentService.update(department);
        return Result.success();
    }


    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result pageSelect(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        PageResult pageResult = departmentService.pageSelect(pageNum, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping
    @ApiOperation("查询所有部门信息")
    public Result getAll() {
        List<Department> departments = departmentService.getAll();
        return Result.success(departments);
    }


}
