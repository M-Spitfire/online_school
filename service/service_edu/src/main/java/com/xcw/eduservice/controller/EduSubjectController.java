package com.xcw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xcw.eduservice.bean.EduSubject;
import com.xcw.eduservice.bean.subject.Subject;
import com.xcw.eduservice.service.EduSubjectService;
import com.xcw.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xcw
 * @since 2020-08-05
 */
@RestController
@CrossOrigin
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

    @GetMapping("/get-all-subject")
    public R getAllSubject(){
        List<Subject> list = subjectService.getAllSubject("0");
        System.out.println(list);
        return R.ok().data("subjects", list);
    }
}

