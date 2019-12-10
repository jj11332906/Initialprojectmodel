package com.wkj.project.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wkj.project.dto.ArticleDTO;
import com.wkj.project.dto.VideoDTO;
import com.wkj.project.entity.Article;
import com.wkj.project.entity.Video;
import com.wkj.project.form.ArticleForm;
import com.wkj.project.form.VideoForm;
import com.wkj.project.mapper.ArticleMapper;
import com.wkj.project.mapper.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class VideoService {

    @Autowired
    VideoMapper videoMapper;

    public Video insert(VideoForm form, String username) {
        Video video = new Video();
        BeanUtils.copyProperties(form,video);
        video.setBaseInfo();
        video.setCreator(username);
        videoMapper.insert(video);
        return video;
    }

    public VideoDTO findDTOByIsDeletedIsFalseAndId(Long id) {

        Video video = videoMapper.findByIsDeletedIsFalseAndId(id);
        VideoDTO videoDTO = VideoDTO.convert(video);
        return videoDTO;
    }

    public Video findVideoById(Long id) {

        Video video = videoMapper.findByIsDeletedIsFalseAndId(id);
        return video;
    }



    public Video update(VideoForm form,Long id) {
        Video entity = findVideoById(id);
        BeanUtils.copyProperties(form,entity);
        entity.setUpdateDate(new Date());
        videoMapper.update(entity);
        return entity;
    }

    public void delete(Video video) {
        videoMapper.delete(video);
    }

    public Page<Video> query(String title,String creator,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Video> videos = videoMapper.query(title,creator);

        return videos;
    }
}
