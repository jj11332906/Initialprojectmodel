package com.wkj.project.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private Boolean isDeleted;
    private Boolean isEnabled;
    private Date createDate;
    private Date updateDate;

    public void setBaseInfo(){
        setCreateDate(new Date());
        setUpdateDate(new Date());
        setIsDeleted(false);
        setIsEnabled(true);
    }
}
