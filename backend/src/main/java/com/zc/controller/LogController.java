package com.zc.controller;

import com.zc.common.result.PageResult;
import com.zc.common.result.Result;
import com.zc.service.LogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
@Slf4j
@Api(tags = "操作日志信息管理")
public class LogController {

    @Autowired
    private LogService logService;


    @GetMapping("/page")
    public Result pageQuery(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return Result.success(logService.pageQuery(pageNum, pageSize));
    }
}
