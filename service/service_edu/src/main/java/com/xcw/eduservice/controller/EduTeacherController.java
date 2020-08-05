package com.xcw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xcw.eduservice.bean.EduTeacher;
import com.xcw.eduservice.bean.vo.TeacherQuery;
import com.xcw.eduservice.service.EduTeacherService;
import com.xcw.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xcw
 * @since 2020-08-02
 */
@RestController
@RequestMapping("/user-service/teacher")
@Api(tags = "讲师管理")
@CrossOrigin
public class EduTeacherController {

    private EduTeacherService teacherService;

    @Autowired
    public EduTeacherController( EduTeacherService service){
        this.teacherService = service;
    }

    //获取所有老师的信息
    @ApiOperation("获取所有讲师的信息")
    @GetMapping("list")
    public R getAll(){
        return R.ok().data("teachers", teacherService.list(null));
    }

    //根据id逻辑删除某个老师
    @ApiOperation("根据id逻辑删除")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id){
        return teacherService.removeById(id) ? R.ok() : R.error();
    }

    //分页查询讲师的信息
    @GetMapping("page/{current}/{limit}")
    public R queryByPage(@PathVariable Long current, @PathVariable Long limit){
            Page<EduTeacher> page = new Page<>(current, limit);
            teacherService.page(page, null);
            return R.ok().data("total", page.getTotal()).data("teacherPage", page.getRecords());
    }

    @PostMapping("/page_condition/{current}/{limit}")
    public R queryTeacherConditionsPage(@PathVariable Long current, @PathVariable Long limit,
                                        @RequestBody(required = false) TeacherQuery conditions){
        Page<EduTeacher> page = new Page<>(current, limit);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = conditions.getName();
        Integer level = conditions.getLevel();
        String start = conditions.getBegin();
        String end = conditions.getEnd();
        if(!StringUtils.isEmpty(name))wrapper.like("name", name);
        if(!StringUtils.isEmpty(level))wrapper.eq("level", level);
        if(!StringUtils.isEmpty(start))wrapper.ge("gmt_create", start);
        if(!StringUtils.isEmpty(end))wrapper.le("gmt_create", end);

        teacherService.page(page, wrapper);
        return R.ok().data("total", page.getTotal()).data("teacherConditionPage", page.getRecords());

    }

    @PostMapping("add")
    public R addTeacher(@RequestBody EduTeacher teacher){
        return teacherService.save(teacher) ? R.ok() : R.error();
    }

    @GetMapping("get/{id}")
    public R getById(@PathVariable String id){
        return R.ok().data("teacher", teacherService.getById(id));
    }

    @PostMapping("update")
    public R updateById(@RequestBody EduTeacher teacher){
        return teacherService.updateById(teacher) ? R.ok() : R.error();
    }
}

