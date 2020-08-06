package com.xcw.eduservice.service;

import com.xcw.eduservice.bean.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xcw.eduservice.bean.subject.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xcw
 * @since 2020-08-05
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addByFile(MultipartFile file, EduSubjectService subjectService);

    List<Subject> getAllSubject(String parentId);
}
