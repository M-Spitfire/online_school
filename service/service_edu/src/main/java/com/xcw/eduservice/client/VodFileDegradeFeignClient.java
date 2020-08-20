package com.xcw.eduservice.client;

import com.xcw.utils.R;
import org.springframework.stereotype.Component;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    //出错时执行
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频出错了");
    }
}
