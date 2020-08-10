package com.xcw.eduservice.controller;


import com.xcw.eduservice.bean.EduChapter;
import com.xcw.eduservice.bean.chapter.Chapter;
import com.xcw.eduservice.service.EduChapterService;
import com.xcw.utils.R;
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
@RequestMapping("/edu-service/chapter")
@CrossOrigin
public class EduChapterController {

    private EduChapterService chapterService;

    @Autowired
    public EduChapterController(EduChapterService chapterService){this.chapterService = chapterService;}

    //获取所有章节和其小节的信息
    @GetMapping("/chapter-info/{courseId}")
    public R getChapterInfo(@PathVariable String courseId){
        List<Chapter> list = chapterService.getChapterInfo(courseId);

        return R.ok().data("chapters",  list);
    }

    //添加或修改章节
    @PostMapping("/save")
    public R saveChapter(@RequestBody EduChapter chapter){
        boolean flag = chapterService.saveOrUpdate(chapter);
        return flag ? R.ok().message("更新成功") : R.error().message("更新失败");
    }

    //根据courseId查询章节信息
    @GetMapping("/queryById/{courseId}")
    public R queryById(@PathVariable String courseId){
        List<Chapter> list = chapterService.queryById(courseId);
        return R.ok().data("chapters", list);
    }

    //删除章节
    @GetMapping("/delete/{chapterId}")
    public R deleteById(@PathVariable String chapterId){
        boolean flag = chapterService.delete(chapterId);
        return flag ? R.ok().message("删除成功") : R.error().message("删除失败");
    }
}

