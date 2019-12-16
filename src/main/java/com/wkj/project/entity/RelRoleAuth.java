package com.wkj.project.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RelRoleAuth implements Serializable {
    private Long roleId;
    private String authority;
}
