package com.wkj.project.entity;

import lombok.Data;

@Data
public class ArticleCatogory extends BaseEntity{
    /**
     * 文章分类ID
     */
    private Long id;
    /**
     * 分类标题
     */
    private String title;

    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者账号
     */
    private String creator;

}
