package com.wkj.project.enums;

public enum AuthorityTypeEnum {
    data_authority("数据权限"),
    access_authority("访问权限"),
    operate_authority("操作权限");

    private String name;

    AuthorityTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
