package com.wkj.project.dto;

import com.wkj.project.entity.SysRole;
import com.wkj.project.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDTO {

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
    /**
     * 该账号关联角色
     */
    private List<SysRoleDTO> sysRoleDTOS;

    public static SysUserDTO convert(SysUser entity,  List<SysRoleDTO> sysRoleDTOS) {
        SysUserDTO dto = new SysUserDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setSysRoleDTOS(sysRoleDTOS);
        return dto;
    }
}
