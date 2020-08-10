package com.xcw.eduservice.bean.chapter;

import lombok.Data;

import java.util.List;

@Data
public class Chapter {
    private String id;

    private String title;

    private List<Video> children;
}
