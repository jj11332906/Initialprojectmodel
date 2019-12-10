package com.wkj.project.resource;

import com.github.pagehelper.Page;
import com.wkj.project.dto.ArticleDTO;
import com.wkj.project.dto.VideoDTO;
import com.wkj.project.entity.Article;
import com.wkj.project.entity.SysUser;
import com.wkj.project.entity.Video;
import com.wkj.project.form.ArticleForm;
import com.wkj.project.form.VideoForm;
import com.wkj.project.service.ArticleService;
import com.wkj.project.service.Oauth2UtilService;
import com.wkj.project.service.VideoService;
import com.wkj.project.util.ErrorCode;
import com.wkj.project.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/video")
@Slf4j
@Api(description = "视频管理")
public class VideoResource {


    @Autowired
    VideoService videoService;

    @Autowired
    Oauth2UtilService oauth2UtilService;


    @GetMapping("query")
    @ApiOperation(value =
            "根据关键字查询数据")
    public Result query(
            Integer pageNum, Integer pageSize, String ts, String queryTitle,String accessToken
    ) {
        log.info("根据关键字查询视频数据");
        log.info("ts: " + ts);
        log.info("queryTitle: " + queryTitle);
        log.info("accessToken: " + accessToken);
        //todo 通过accessToken获取用户信息，通过用户信息可以控制数据访问权限
        SysUser sysUser = oauth2UtilService.getUserByToken(accessToken);
        String creator = sysUser.getUsername();
        Page<Video> videos = videoService.query(queryTitle,creator, pageNum, pageSize);
        List<VideoDTO> videoDTOS = new ArrayList<>();
        List<Video> videoList = videos.getResult();
        videoList.forEach(video -> {
            VideoDTO dto = VideoDTO.convert(video);
            videoDTOS.add(dto);
        });

        return Result.getResult(ErrorCode.OP_SUCCESS, videoDTOS);
    }

    @GetMapping("totalPage")
    @ApiOperation(value =
            "总页数")
    public Result totalPage(
            Integer pageSize, String ts, String title,String accessToken
    ) {
        //todo 通过accessToken获取用户信息，通过用户信息可以控制数据访问权限
        SysUser sysUser = oauth2UtilService.getUserByToken(accessToken);
        String creator = sysUser.getUsername();
        int pageNum = 1;
        Page<Video> videos = videoService.query(title,creator, pageNum, pageSize);
        int pages = videos.getPages();
        return Result.getResult(ErrorCode.OP_SUCCESS, pages);
    }


    @PutMapping("put/{id}")
    @ApiOperation(value = "修改视频信息")
    public Result update(
            @ModelAttribute("Video") VideoForm form,
            @PathVariable @ApiParam("videoId") Long id

    ) {
        log.info("修改视频信息");
        log.info("id：" + id);
        // TODO 修改用户账号基础信息
        Video entity = videoService.update(form, id);

        return Result.getResult(ErrorCode.OP_SUCCESS, VideoDTO.convert(entity));
    }


    @PutMapping("delete")
    @ApiOperation(value = "删除")
    public Result delete(
            String id
    ) {
        log.info("删除视频");
        log.info("videoId：" + id);

        Video video = videoService.findVideoById(Long.valueOf(id));
        videoService.delete(video);

        return Result.getResult(ErrorCode.OP_SUCCESS, video);
    }

    @GetMapping("find/{id}")
    @ApiOperation(value = "根据id获取信息")
    public Result get(
            @PathVariable @ApiParam("视频id") Long id
    ) {
        VideoDTO dto = videoService.findDTOByIsDeletedIsFalseAndId(id);
        return Result.getResult(ErrorCode.OP_SUCCESS, dto);
    }


    @PostMapping("add")
    @ResponseBody
    @ApiOperation(value = "添加视频")
    //todo 注意这里对接口访问权限配置
    @PreAuthorize("hasAnyAuthority('sysadmin','videoMng','videoAdd')")
    public Result add(
            @ModelAttribute("Video") VideoForm form
    ) {
        log.info("添加视频");
        log.info("title：" + form.getVideoTitle());
        //todo 通过accessToken获取用户信息，通过用户信息可以控制数据访问权限
        SysUser sysUser = oauth2UtilService.getUserByToken(form.getAccessToken());
        Video video = videoService.insert(form,sysUser.getUsername());
        return Result.getResult(ErrorCode.OP_SUCCESS, video);
    }

}
