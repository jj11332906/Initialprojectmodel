package com.wkj.project.dto;

import com.wkj.project.entity.Article;
import com.wkj.project.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    /**
     * 文章ID
     */
    private Long id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容，存的是html
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者账号
     */
    private String creator;
    /**
     * 创建时间
     */
    private String createDateStr;


    public static ArticleDTO convert(Article entity) {
        ArticleDTO dto = new ArticleDTO();
        BeanUtils.copyProperties(entity, dto);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setCreateDateStr(sdf.format(entity.getCreateDate()));
        return dto;
    }
}
