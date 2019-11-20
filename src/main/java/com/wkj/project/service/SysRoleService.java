package com.wkj.project.service;

import com.wkj.project.dto.SysRoleDTO;
import com.wkj.project.entity.RelRoleAuth;
import com.wkj.project.entity.SysRole;
import com.wkj.project.mapper.RelRoleAuthMapper;
import com.wkj.project.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    RelRoleAuthMapper relRoleAuthMapper;

    public List<SysRoleDTO> findAll() {
        List<SysRole> sysRoles = sysRoleMapper.findAll();
        List<SysRoleDTO> sysRoleDTOS = new ArrayList<>();
        for(SysRole role : sysRoles) {
            List<RelRoleAuth> relRoleAuths = relRoleAuthMapper.findRelRoleAuthsByRoleIds(role.getId().toString());

        }
        return sysRoleDTOS;
    }

}
