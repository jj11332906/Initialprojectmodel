package com.wkj.project.resource;

import com.github.pagehelper.Page;
import com.wkj.project.dto.ArticleDTO;
import com.wkj.project.entity.Article;
import com.wkj.project.form.ArticleForm;
import com.wkj.project.service.ArticleService;
import com.wkj.project.util.ErrorCode;
import com.wkj.project.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/article")
@Slf4j
@Api(description = "文章管理")
public class ArticleResource {


    @Autowired
    ArticleService articleService;


    @GetMapping("query")
    @ApiOperation(value =
            "根据关键字查询数据")
    public Result query(
            Integer pageNum, Integer pageSize, String ts, String title
    ) {
        log.info("根据关键字查询菜单数据");
        log.info("ts: " + ts);
        log.info("title: " + title);
        Page<Article> articles = articleService.query(title, pageNum, pageSize);
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        List<Article> articleList = articles.getResult();
        articleList.forEach(article -> {
            ArticleDTO dto = ArticleDTO.convert(article);
            articleDTOS.add(dto);
        });

        return Result.getResult(ErrorCode.OP_SUCCESS, articleDTOS);
    }

    @GetMapping("totalPage")
    @ApiOperation(value =
            "总页数")
    public Result totalPage(
            Integer pageSize, String ts, String title
    ) {

        int pageNum = 1;
        Page<Article> articlePage = articleService.query(title, pageNum, pageSize);
        int pages = articlePage.getPages();
        return Result.getResult(ErrorCode.OP_SUCCESS, pages);
    }


    @PutMapping("put/{id}")
    @ApiOperation(value = "修改文章信息")
    public Result update(
            @ModelAttribute("Article") ArticleForm form,
            @PathVariable @ApiParam("articleId") Long id

    ) {
        log.info("修改文章信息");
        log.info("id：" + id);
        // TODO 修改用户账号基础信息
        Article entity = articleService.update(form, id);

        return Result.getResult(ErrorCode.OP_SUCCESS, ArticleDTO.convert(entity));
    }


    @PutMapping("delete")
    @ApiOperation(value = "删除")
    public Result delete(
            String id
    ) {
        log.info("删除文章");
        log.info("articleId：" + id);

        Article article = articleService.findArticleById(Long.valueOf(id));
        articleService.delete(article);

        return Result.getResult(ErrorCode.OP_SUCCESS, article);
    }

    @GetMapping("find/{id}")
    @ApiOperation(value = "根据id获取信息")
    public Result get(
            @PathVariable @ApiParam("文章id") Long id
    ) {
        ArticleDTO entity = articleService.findDTOByIsDeletedIsFalseAndId(id);
        return Result.getResult(ErrorCode.OP_SUCCESS, entity);
    }

    @PostMapping("add")
    @ResponseBody
    @ApiOperation(value = "添加文章")
    public Result add(
            @ModelAttribute("Article") ArticleForm form
    ) {
        log.info("添加文章");
        log.info("title：" + form.getTitle());
        log.info("content：" + form.getContent());
        log.info("remark:" + form.getRemark());
        Article article = articleService.insert(form);

        return Result.getResult(ErrorCode.OP_SUCCESS, article);

    }

}
