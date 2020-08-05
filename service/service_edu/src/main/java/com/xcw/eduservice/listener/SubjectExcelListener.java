package com.xcw.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xcw.eduservice.bean.EduSubject;
import com.xcw.eduservice.bean.excel.SubjectData;
import com.xcw.eduservice.service.EduSubjectService;
import com.xcw.servicebase.exception.MyException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(EduSubjectService subjectService){this.subjectService = subjectService;}

    //读取excel数据
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new MyException(20003, "文件数据为空");
        }
        //一行一行读
        EduSubject oneLevelSubject = existOneLevelName(subjectData.getOneLevelName());
        //如果数据库中没有一级分类,添加进去
        if(oneLevelSubject == null){
            oneLevelSubject = new EduSubject();
            oneLevelSubject.setParentId("0");
            oneLevelSubject.setTitle(subjectData.getOneLevelName());
            subjectService.save(oneLevelSubject);
        }
        EduSubject twoLevelSubject = existTwoLevelName(subjectData.getTwoLevelName(), oneLevelSubject.getId());
        if(twoLevelSubject == null){
            twoLevelSubject = new EduSubject();
            twoLevelSubject.setTitle(subjectData.getTwoLevelName());
            twoLevelSubject.setParentId(oneLevelSubject.getId());
            subjectService.save(twoLevelSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断是否一级名称已经存在
    private EduSubject existOneLevelName(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name).eq("parent_id", 0);
        return subjectService.getOne(wrapper);
    }

    //判断是否二级名称已经存在
    private EduSubject existTwoLevelName(String name, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name).eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }
}
