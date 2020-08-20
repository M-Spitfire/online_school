package com.xcw.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {
        //根据视频id获取视频播放地址

        //创建初始化对象(这里的id和secretKey实际上是oss的访存密钥)
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G4d11BtQ9tQxg585QnR", "vupFgf0KJu9hHbB38vvFRJds1bIR2N");

        //创建获取视频地址request和response
//        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
//        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向request对象中设置视频id
        request.setVideoId("videoId");

        //调用初始化对象中的方法获取信息
        response = client.getAcsResponse(request);
//        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        //获取视频凭证,用于播放加密视频
        System.out.println(response.getPlayAuth());

//        for(GetPlayInfoResponse.PlayInfo info : playInfoList){
//            //获取视频播放的url
//            System.out.println(info.getPlayURL());
//        }
//        //获取base信息中的视频名字
//        System.out.println(response.getVideoBase().getTitle());

    }
}
