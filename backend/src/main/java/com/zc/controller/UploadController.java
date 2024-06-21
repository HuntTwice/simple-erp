package com.zc.controller;


import com.zc.common.result.Result;
import com.zc.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Api(tags = "文件上传")
public class UploadController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String url = aliOSSUtils.upload(file);
        return Result.success(url);
    }
}
