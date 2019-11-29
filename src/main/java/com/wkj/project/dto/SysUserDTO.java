package com.wkj.project.dto;

import com.wkj.project.entity.SysRole;
import com.wkj.project.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     * 创建时间
     */
    private String createDateStr;
    /**
     * 所属角色
     */
    private String roleName;
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 该账号关联角色
     */
    private List<SysRoleDTO> sysRoleDTOS;

    public static SysUserDTO convert(SysUser entity,  List<SysRoleDTO> sysRoleDTOS) {
        SysUserDTO dto = new SysUserDTO();
        BeanUtils.copyProperties(entity, dto);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setCreateDateStr(sdf.format(entity.getCreateDate()));
        dto.setSysRoleDTOS(sysRoleDTOS);
        SysRoleDTO sysRoleDTO = sysRoleDTOS.get(0);
        dto.setRoleName(sysRoleDTO.getName());
        dto.setRoleId(sysRoleDTO.getId().toString());
        return dto;
    }
}
