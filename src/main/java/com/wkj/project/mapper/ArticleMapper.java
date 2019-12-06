package com.wkj.project.mapper;

import com.github.pagehelper.Page;
import com.wkj.project.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface ArticleMapper {

    Map findById(@Param("id") Long id);

    Long insert(Article article);

    Article findByIsDeletedIsFalseAndId(@Param("id") Long id);

    void delete(Article article);

    Page<Article> query(@Param("title") String title,@Param("creator") String creator);

    void update(Article article);
}
