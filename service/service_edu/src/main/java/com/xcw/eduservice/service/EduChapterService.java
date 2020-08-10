package com.xcw.eduservice.service;

import com.xcw.eduservice.bean.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xcw.eduservice.bean.chapter.Chapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xcw
 * @since 2020-08-06
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> getChapterInfo(String courseId);

    boolean delete(String chapterId);

    List<Chapter> queryById(String courseId);
}
