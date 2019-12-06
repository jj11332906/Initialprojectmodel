package com.wkj.project.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wkj.project.dto.ArticleDTO;
import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.dto.SysRoleDTO;
import com.wkj.project.dto.SysUserDTO;
import com.wkj.project.entity.Article;
import com.wkj.project.entity.RelRoleAuth;
import com.wkj.project.entity.SysRole;
import com.wkj.project.entity.SysUser;
import com.wkj.project.form.ArticleForm;
import com.wkj.project.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    public Article insert(ArticleForm form,String username) {
        Article article = new Article();
        BeanUtils.copyProperties(form,article);
        article.setBaseInfo();
        article.setCreator(username);
        articleMapper.insert(article);
        return article;
    }

    public ArticleDTO findDTOByIsDeletedIsFalseAndId(Long id) {

        Article article = articleMapper.findByIsDeletedIsFalseAndId(id);
        ArticleDTO articleDTO = ArticleDTO.convert(article);
        return articleDTO;
    }

    public Article findArticleById(Long id) {

        Article article = articleMapper.findByIsDeletedIsFalseAndId(id);
        return article;
    }



    public Article update(ArticleForm form,Long id) {
        Article entity = findArticleById(id);
        BeanUtils.copyProperties(form,entity);
        entity.setUpdateDate(new Date());
        articleMapper.update(entity);
        return entity;
    }

    public void delete(Article article) {
        articleMapper.delete(article);
    }

    public Page<Article> query(String title,String creator,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Article> articles = articleMapper.query(title,creator);

        return articles;
    }
}
