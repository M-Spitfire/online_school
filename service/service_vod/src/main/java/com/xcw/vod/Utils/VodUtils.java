package com.xcw.vod.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VodUtils implements InitializingBean {
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Value("${aliyun.vod.file.keyid}")
    private String id;

    @Value("${aliyun.vod.file.keysecret}")
    private String secret;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = id;
        ACCESS_KEY_SECRET = secret;
    }
}
