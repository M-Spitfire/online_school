package com.xcw.eduservice.bean.subject;

import lombok.Data;

import java.util.List;

@Data
public class Subject {
    private String id;
    private String title;
    List<Subject> children;
}
