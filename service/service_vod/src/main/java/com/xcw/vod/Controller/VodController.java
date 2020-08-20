package com.xcw.vod.Controller;

import com.xcw.utils.R;
import com.xcw.vod.Service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/vod/video")
@CrossOrigin
public class VodController {

    private VodService vodService;

    @Autowired
    public VodController(VodService vodService){this.vodService = vodService;}

    //上传视频到阿里云
    @PostMapping("/upload")
    public R uploadVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    //根据视频id删除阿里云的视频
    @DeleteMapping("/delete/{id}")
    public R deleteVideo(@PathVariable String id){
        //失败直接抛异常, 不会回到这里了
        vodService.deleteVideo(id);
        return R.ok().message("视频删除成功");
    }
}
