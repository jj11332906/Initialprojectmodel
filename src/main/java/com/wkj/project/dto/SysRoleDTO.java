package com.wkj.project.dto;

import com.wkj.project.entity.SysAuthority;
import com.wkj.project.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 角色名
     */
    private String name;

    /**
     * 该角色关联的权限，权限结构：如果关联了父节点，即便子节点全部关联也要全部写入authorityDTOS，如果有某一个字节点必包括父节点
     */
    private List<SysAuthorityDTO> authorityDTOS;

    public static SysRoleDTO convert(SysRole entity,List<SysAuthorityDTO> sysAuthorityDTOS){
        SysRoleDTO dto = new SysRoleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthorityDTOS(sysAuthorityDTOS);
        return dto;
    }
}
