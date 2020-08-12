package com.xcw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcw.eduservice.bean.EduCourse;
import com.xcw.eduservice.bean.EduVideo;
import com.xcw.eduservice.bean.vo.CourseInfo;
import com.xcw.eduservice.bean.vo.CourseQuery;
import com.xcw.eduservice.client.VodClient;
import com.xcw.eduservice.service.EduCourseDescriptionService;
import com.xcw.eduservice.service.EduCourseService;
import com.xcw.eduservice.service.EduVideoService;
import com.xcw.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    private VodClient vodClient;
    private EduVideoService videoService;

    @Autowired
    public EduCourseController(EduCourseService courseService, VodClient vodClient, EduVideoService videoService){
        this.courseService = courseService;
        this.vodClient = vodClient;
        this.videoService = videoService;
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
        //查询课程下的所有视频
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        wrapper.select("video_source_id");//只查询video_source_id
        List<EduVideo> videos = videoService.list(wrapper);

        //拼接videoId
        StringBuilder ids = new StringBuilder();
        for(EduVideo eduVideo : videos){
            String sourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(sourceId)){
                ids.append(sourceId + ',');
            }
        }
        //去掉最后的逗号
        ids.deleteCharAt(ids.length() - 1);

        //调用vod服务的方法删除阿里云上的视频 ----> 数据库中的视频id交给后面做
        String strIds = ids.toString();
        if(!StringUtils.isEmpty(strIds)){
            System.out.println(strIds);
            vodClient.deleteVideo(ids.toString());
        }

        boolean flag = courseService.deleteCourseById(id);
        return flag ? R.ok() : R.error();
    }

}

