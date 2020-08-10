package com.xcw.eduservice.controller;


import com.xcw.eduservice.bean.EduVideo;
import com.xcw.eduservice.service.EduVideoService;
import com.xcw.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author xcw
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/edu-service/video")
@CrossOrigin
public class EduVideoController {

    private EduVideoService videoService;

    @Autowired
    public EduVideoController(EduVideoService videoService){this.videoService = videoService;}

    //添加或修改
    @PostMapping("/save")
    public R saveVideo(@RequestBody EduVideo video){
        videoService.saveOrUpdate(video);
        return R.ok();
    }

    //删除
    @GetMapping("/delete/{id}")
    public R deleteById(@PathVariable String id){
        videoService.removeById(id);
        return R.ok();
    }
}

