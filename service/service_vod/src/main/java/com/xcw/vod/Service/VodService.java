package com.xcw.vod.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VodService {
    String uploadVideo(MultipartFile file);

    boolean deleteVideo(String id);
}
