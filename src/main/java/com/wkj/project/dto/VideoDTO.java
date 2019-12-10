package com.wkj.project.dto;

import com.wkj.project.entity.Article;
import com.wkj.project.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    /**
     * 视频ID
     */
    private Long id;
    /**
     * 视频标题
     */
    private String videoTitle;
    /**
     * 视频路径
     */
    private String videoPath;
    /**
     * 视频MP5
     */
    private String videoMp5;

    /**
     * 创建者账号
     */
    private String creator;
    /**
     * 创建时间
     */
    private String createDateStr;


    public static VideoDTO convert(Video entity) {
        VideoDTO dto = new VideoDTO();
        BeanUtils.copyProperties(entity, dto);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setCreateDateStr(sdf.format(entity.getCreateDate()));
        return dto;
    }
}
