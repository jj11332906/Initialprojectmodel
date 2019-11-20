package com.wkj.project.entity;


import lombok.Data;

@Data
public class SysMenu {

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
    private SysMenu parentMenu;

    /**
     * 是否跳转
     */
    private Boolean isHref;

    /**
     * 关联权限
     */
    private SysAuthority relationAuthority;

}
