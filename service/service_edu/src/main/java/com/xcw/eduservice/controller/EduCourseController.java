package com.xcw.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcw.eduservice.bean.EduCourse;
import com.xcw.eduservice.bean.vo.CourseInfo;
import com.xcw.eduservice.bean.vo.CourseQuery;
import com.xcw.eduservice.service.EduCourseDescriptionService;
import com.xcw.eduservice.service.EduCourseService;
import com.xcw.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xcw
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/edu-service/course")
@CrossOrigin
public class EduCourseController {
    private EduCourseService courseService;

    @Autowired
    public EduCourseController(EduCourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/add-course-info")
    public R addCourseInfo(@RequestBody CourseInfo info){
        String courseId = courseService.add(info);
        return R.ok().data("courseId", courseId);
    }

    @GetMapping("/getCourseAndDescription/{id}")
    public R getCourseAndDescription(@PathVariable String id){
        return R.ok().data("course", courseService.getCourseAndDescription(id));
    }

    @GetMapping("/publish/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        boolean flag = courseService.publishById(courseId);
        return flag ? R.ok() : R.error();
    }

    @ApiOperation(value = "分页课程列表")
    @PostMapping("/page/{page}/{limit}")
    public R pageQuery(
        @ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,
        @ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit,
        @ApiParam(name = "courseQuery", value = "查询对象", required = false) @RequestBody
        CourseQuery courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @DeleteMapping("/delete/{id}")
    public R deleteCourseById(@PathVariable String id){
        System.out.println(id);
        boolean flag = courseService.deleteCourseById(id);
        return flag ? R.ok() : R.error();
    }

}

