package com.wkj.project.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.wkj.project.enums.AuthorityTypeEnum;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysAuthority implements GrantedAuthority {

    public String authority; //权限码

    public String description; //权限标识
    public AuthorityTypeEnum authorityType;

    List<SysAuthority> children;


    public SysAuthority() {
    }

    public SysAuthority(String authority) {
        this.authority = authority;
    }

    public static List<GrantedAuthority> convert(String authoritiesString) {
        if (authoritiesString != null) {
            return Arrays.stream(authoritiesString.split(","))
                    .map(SysAuthority::new)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<SysAuthority> getChildren() {
        return children;
    }

    public void setChildren(List<SysAuthority> children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuthorityTypeEnum getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityTypeEnum authorityType) {
        this.authorityType = authorityType;
    }


}
