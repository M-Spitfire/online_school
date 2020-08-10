package com.xcw.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xcw.oss.service.OssService;
import com.xcw.oss.utils.ConstantPropertyUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadAvatar(MultipartFile file) {
        //获取信息值
        String endpoint = ConstantPropertyUtil.END_POINT;
        String keyId = ConstantPropertyUtil.ACCESS_KEY_ID;
        String keySecret = ConstantPropertyUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertyUtil.BUCKET_NAME;
        //在文件名前面加一个uuid,防止重名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + file.getOriginalFilename();

        try {
            //创建OSS实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);

            //将文件读取为流
            InputStream is = file.getInputStream();

            //上传,参数:(目标仓库的名字, 给文件起一个名字, 流)
            //如果文件名的格式类似与文件的磁盘路径,比如2020/01/01/01.jpg, 那么会自动创建三级目录2020/01/01,将01.jpg存放在里面
            //利用这点我们可以根据上传的日期来将图片分类
            fileName = new DateTime().toString("yyyy/MM/dd") + "/" + fileName;
            ossClient.putObject(bucketName, fileName, is);

            //关闭oss
            ossClient.shutdown();

            //返回文件的访问路径
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
