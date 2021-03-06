package com.wkj.project.entity;

import lombok.Data;

@Data
public class Article extends BaseEntity{
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

}
