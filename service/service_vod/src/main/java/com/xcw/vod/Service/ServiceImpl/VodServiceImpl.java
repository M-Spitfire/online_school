package com.xcw.vod.Service.ServiceImpl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.xcw.servicebase.exception.MyException;
import com.xcw.vod.Service.VodService;
import com.xcw.vod.Utils.InitVodClient;
import com.xcw.vod.Utils.VodUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(VodUtils.ACCESS_KEY_ID,VodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            //不管成功还是失败都会返回视频的id, 失败的话会有错误码等其他信息
            videoId = response.getVideoId();
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean deleteVideo(String id) {
        try {
            //获取客户端
            DefaultAcsClient client = InitVodClient.initVodClient(VodUtils.ACCESS_KEY_ID, VodUtils.ACCESS_KEY_SECRET);

            //创建request
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            //传入要删除的视频的id, 如果多个用逗号隔开(不是多个字符串,是一个字符串)
            deleteVideoRequest.setVideoIds(id);

            //创建一个空的response
            DeleteVideoResponse response = new DeleteVideoResponse();
            //执行删除, 并获取response
            response = client.getAcsResponse(deleteVideoRequest);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(20001, "删除视频失败");
        }
    }
}
