package com.wkj.project.entity;

import lombok.Data;

@Data
public class Article {
    private Long id;
    private String title;
    private String content;
    private String remark;

    public Article(String title, String content, String remark) {
        super();
        this.title = title;
        this.content = content;
        this.remark = remark;
    }
}
