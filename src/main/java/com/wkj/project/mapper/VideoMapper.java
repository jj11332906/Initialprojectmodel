package com.wkj.project.mapper;

import com.github.pagehelper.Page;
import com.wkj.project.entity.Article;
import com.wkj.project.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface VideoMapper {

    Map findById(@Param("id") Long id);

    Long insert(Video video);

    Video findByIsDeletedIsFalseAndId(@Param("id") Long id);

    void delete(Video video);

    Page<Video> query(@Param("title") String title, @Param("creator") String creator);

    void update(Video video);
}
