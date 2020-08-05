package com.xcw.eduservice.controller;


import com.xcw.eduservice.service.EduSubjectService;
import com.xcw.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xcw
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/edu-service/subject")
public class EduSubjectController {
    private EduSubjectService subjectService;

    @Autowired
    public EduSubjectController(EduSubjectService subjectService){this.subjectService = subjectService;}

    @PostMapping("/add")
    public R addSubjects(MultipartFile file){
        subjectService.addByFile(file, subjectService);
        return R.ok();
    }
}

