package com.xcw.eduservice.client;

import com.xcw.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    //调用service_vod中的方法,根据视频id删除阿里云的视频
    //调用其他服务的方法时,@PathVariable注解一定要指定参数名称,否则会报错
    @DeleteMapping("/vod/video/delete/{id}")
    public R deleteVideo(@PathVariable("id") String id);
}
