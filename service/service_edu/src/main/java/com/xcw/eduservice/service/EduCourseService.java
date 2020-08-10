package com.xcw.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcw.eduservice.bean.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xcw.eduservice.bean.vo.CourseInfo;
import com.xcw.eduservice.bean.vo.CourseQuery;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xcw
 * @since 2020-08-06
 */
public interface EduCourseService extends IService<EduCourse> {

    String add(CourseInfo info);

    boolean publishById(String courseId);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    CourseInfo getCourseAndDescription(String id);

    boolean deleteCourseById(String id);
}
