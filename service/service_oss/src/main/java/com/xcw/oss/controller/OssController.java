package com.xcw.oss.controller;

import com.xcw.oss.service.OssService;
import com.xcw.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "阿里云oss")
@RestController
@CrossOrigin
@RequestMapping("/oss/file")
public class OssController {

    private OssService ossService;

    @Autowired
    public OssController(OssService ossService){this.ossService = ossService;}

    //上传头像
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        String path = ossService.uploadAvatar(file);
        return R.ok().data("path", path);
    }
}
