package com.wkj.project.entity;


import lombok.Data;

@Data
public class SysRole extends BaseEntity {

    /**
     * id
     */
    private Long id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;


}
