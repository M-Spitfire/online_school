package com.xcw.eduservice.service.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xcw.eduservice.bean.EduSubject;
import com.xcw.eduservice.bean.excel.SubjectData;
import com.xcw.eduservice.bean.subject.Subject;
import com.xcw.eduservice.listener.SubjectExcelListener;
import com.xcw.eduservice.mapper.EduSubjectMapper;
import com.xcw.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xcw
 * @since 2020-08-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addByFile(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Subject> getAllSubject(String parentId) {
        List<Subject> res = new ArrayList<>();
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<EduSubject> list = this.list(wrapper);
        for(EduSubject subject : list){
            Subject temp = new Subject();
            temp.setId(subject.getId());
            temp.setTitle(subject.getTitle());
            temp.setChildren(getAllSubject(subject.getId()));
            res.add(temp);
        }
        return res;
    }
}
