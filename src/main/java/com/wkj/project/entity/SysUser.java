package com.wkj.project.entity;


import lombok.Data;

@Data
public class SysUser {

    /**
     * id
     */
    private Long id;
    /**
     * 姓名
     */
    private String username;
    /**
     * 登录名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 描述
     */
    private String describe;
    /**
     * 邮箱
     */
    private String email;

}
