package com.wkj.project.entity;


import lombok.Data;

@Data
public class SysMenu extends BaseEntity{

    /**
     * id
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 访问地址
     */
    private String menuUrl;
    /**
     * 父级菜单
     */
    private Long groupMenuId;

    /**
     * 是否为分组
     */
    private Boolean isGroup;

    /**
     * 关联权限
     */
    private String relationAuthority;

    /**
     * 描述
     */
    private String description;
    /**
     * 顺序
     */
    private Long sort;

}
