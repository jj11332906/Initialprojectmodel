package com.wkj.project.dto;

import com.wkj.project.entity.SysAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysAuthorityDTO {
    public String authority; //权限码

    public String description; //权限标识

    public static SysAuthorityDTO convert(SysAuthority entity){
        if(entity == null){
            System.out.println("11111");
        }
        SysAuthorityDTO dto = new SysAuthorityDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
