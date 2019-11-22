package com.wkj.project.dto;

import lombok.Data;

import java.util.List;

@Data
public class BootStrapTreeViewAuthorityNodeDTO {
    public String text; //权限码
    public String[] tags; //权限码

    List<BootStrapTreeViewAuthorityNodeDTO> nodes;
}
