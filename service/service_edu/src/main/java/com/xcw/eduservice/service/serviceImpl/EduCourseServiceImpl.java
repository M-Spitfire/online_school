package com.xcw.eduservice.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcw.eduservice.bean.EduChapter;
import com.xcw.eduservice.bean.EduCourse;
import com.xcw.eduservice.bean.EduCourseDescription;
import com.xcw.eduservice.bean.EduVideo;
import com.xcw.eduservice.bean.vo.CourseInfo;
import com.xcw.eduservice.bean.vo.CourseQuery;
import com.xcw.eduservice.mapper.EduCourseMapper;
import com.xcw.eduservice.service.EduChapterService;
import com.xcw.eduservice.service.EduCourseDescriptionService;
import com.xcw.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xcw.eduservice.service.EduVideoService;
import com.xcw.servicebase.exception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xcw
 * @since 2020-08-06
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    private EduCourseDescriptionService descriptionService;
    private EduChapterService chapterService;
    private EduVideoService videoService;

    @Autowired
    public EduCourseServiceImpl(EduCourseDescriptionService descriptionService, EduChapterService chapterService, EduVideoService videoService){
        this.descriptionService = descriptionService;
        this.chapterService = chapterService;
        this.videoService = videoService;
    }

    @Override
    public String add(CourseInfo info) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(info, course);
        boolean isSuccess = this.saveOrUpdate(course);
        if(!isSuccess){
            throw new MyException(20001, "添加课程信息失败");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setId(course.getId());
        description.setDescription(info.getDescription());
        descriptionService.saveOrUpdate(description);

        return course.getId();
    }

    @Override
    public boolean publishById(String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        return this.updateById(course);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public CourseInfo getCourseAndDescription(String id) {
        CourseInfo info = new CourseInfo();
        EduCourse course = this.getById(id);
        BeanUtils.copyProperties(course, info);
        info.setDescription(descriptionService.getById(id).getDescription());
        return info;
    }

    @Override
    public boolean deleteCourseById(String id) {
        boolean flag = true;

        //查询课程ia的所有章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", id);
        List<EduChapter> chapters = chapterService.list(chapterWrapper);

        //删除章节下的所有小节
        for(EduChapter eduChapter : chapters){
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("chapter_id", eduChapter.getId());
            videoQueryWrapper.eq("course_id", id);
            flag = videoService.remove(videoQueryWrapper);
            if(!flag){
                throw new MyException(20001, "删除失败");
            }
        }

        //删除所有章节
        flag = chapterService.remove(chapterWrapper);
        if(!flag){
            throw new MyException(20001, "删除失败");
        }

        //删除课程描述
        flag = descriptionService.removeById(id);
        if(!flag){
            throw new MyException(20001, "删除失败");
        }

        //删除课程
        return this.removeById(id);

    }
}
