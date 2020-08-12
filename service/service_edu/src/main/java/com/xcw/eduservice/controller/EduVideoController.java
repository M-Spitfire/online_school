package com.xcw.eduservice.controller;


import com.xcw.eduservice.bean.EduVideo;
import com.xcw.eduservice.client.VodClient;
import com.xcw.eduservice.service.EduVideoService;
import com.xcw.servicebase.exception.MyException;
import com.xcw.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    private VodClient vodClient;

    @Autowired
    public EduVideoController(EduVideoService videoService, VodClient vodClient){
        this.videoService = videoService;
        this.vodClient = vodClient;
    }

    //添加或修改
    @PostMapping("/save")
    public R saveVideo(@RequestBody EduVideo video){
        videoService.saveOrUpdate(video);
        return R.ok();
    }

    //删除
    @GetMapping("/delete/{id}")
    public R deleteById(@PathVariable String id){
        //获取小节下的视频的id
        String videoSourceId = videoService.getById(id).getVideoSourceId();
        //通过feign调用其他服务的接口删除阿里云上的视频
        if(!StringUtils.isEmpty(videoSourceId)){
            R returnVal = vodClient.deleteVideo(videoSourceId);
            if(returnVal.getCode() != 20000){
                throw new MyException(20001, "熔断");
            }
        }

        //删除小节
        videoService.removeById(id);
        return R.ok();
    }
}

