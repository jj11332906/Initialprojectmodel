package com.wkj.project.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    private Boolean isDeleted;
    private Boolean isEnabled;
    private Date createDate;
    private Date updateDate;
}
