package com.wkj.project.entity;

import lombok.Data;

@Data
public class Video extends BaseEntity{
    private Long id;
    private String videoTitle;
    private String videoPath;
    private String videoMp5;
    private String creator;
}
