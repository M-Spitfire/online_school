package com.xcw.eduservice.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xcw.eduservice.bean.EduChapter;
import com.xcw.eduservice.bean.EduVideo;
import com.xcw.eduservice.bean.chapter.Chapter;
import com.xcw.eduservice.bean.chapter.Video;
import com.xcw.eduservice.mapper.EduChapterMapper;
import com.xcw.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xcw.eduservice.service.EduVideoService;
import com.xcw.servicebase.exception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    private EduVideoService videoService;

    @Autowired
    public EduChapterServiceImpl(EduVideoService videoService){this.videoService = videoService;}

    @Override
    public List<Chapter> getChapterInfo(String courseId) {
        //查询章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper();
        wrapper.eq("course_id", courseId);
        List<EduChapter> list = this.list(wrapper);

        //查询每个章节的小节,并封装
        List<Chapter> res = new ArrayList<>();
        for(EduChapter eduChapter : list){
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(eduChapter, chapter);

            //查询这个章节下的小节(视频)
            QueryWrapper<EduVideo> wrapper1 = new QueryWrapper();
            wrapper1.eq("chapter_id", chapter.getId());
            List<EduVideo> videos = videoService.list(wrapper1);

            //将EduVideo封装成Video
            List<Video> children = new ArrayList<>();
            for(EduVideo eduVideo : videos){
                Video temp = new Video();
                BeanUtils.copyProperties(eduVideo, temp);
                children.add(temp);
            }

            chapter.setChildren(children);
            res.add(chapter);
        }
        return res;
    }

    @Override
    public boolean delete(String chapterId) {
        //查询该章节下是否还有小节内容没有删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        EduVideo video = videoService.getOne(wrapper);
        if(video != null){
            //还有小节没有删除,不允许删除该章节
            throw new MyException(20001, "该章节中还有小节内容哦~");
        }

        //该章节下没有内容,删除该章节
        return this.removeById(chapterId);
    }

    @Override
    public List<Chapter> queryById(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduChapter> list = this.list(wrapper);
        List<Chapter> res = new ArrayList<>();
        for(EduChapter eduChapter : list){
            Chapter temp = new Chapter();
            BeanUtils.copyProperties(eduChapter, temp);
            res.add(temp);
        }
        return res;
    }
}
