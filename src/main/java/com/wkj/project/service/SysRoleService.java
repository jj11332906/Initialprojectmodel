package com.wkj.project.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.dto.SysRoleDTO;
import com.wkj.project.entity.RelRoleAuth;
import com.wkj.project.entity.SysRole;
import com.wkj.project.mapper.RelRoleAuthMapper;
import com.wkj.project.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    RelRoleAuthMapper relRoleAuthMapper;

    public List<SysRoleDTO> findAll() {
        List<SysRole> sysRoles = sysRoleMapper.findAll();
        List<SysRoleDTO> sysRoleDTOS = new ArrayList<>();
        for(SysRole role : sysRoles) {
            List<RelRoleAuth> relRoleAuths = relRoleAuthMapper.findRelRoleAuthsByRoleId(role.getId().toString());

            List<SysAuthorityDTO> sysAuthorityDTOS = new ArrayList<>();
            for(RelRoleAuth relRoleAuth : relRoleAuths){
                SysAuthorityDTO sa = authorityService.findAuthorityDTOByAuthorityCode(relRoleAuth.getAuthority());
                sysAuthorityDTOS.add(sa);
            }

            SysRoleDTO sysRoleDTO = SysRoleDTO.convert(role,sysAuthorityDTOS);
            sysRoleDTOS.add(sysRoleDTO);
        }
        return sysRoleDTOS;
    }

    public Long insert(SysRole sysRole) {
        return sysRoleMapper.insert(sysRole);
    }

    public SysRoleDTO findByIsDeletedIsFalseAndId(Long id) {

        SysRole role = sysRoleMapper.findByIsDeletedIsFalseAndId(id);

        List<RelRoleAuth> relRoleAuths = relRoleAuthMapper.findRelRoleAuthsByRoleId(role.getId().toString());

        List<SysAuthorityDTO> sysAuthorityDTOS = new ArrayList<>();

        for(RelRoleAuth relRoleAuth : relRoleAuths){
            SysAuthorityDTO sa = authorityService.findAuthorityDTOByAuthorityCode(relRoleAuth.getAuthority());
            sysAuthorityDTOS.add(sa);
        }

        SysRoleDTO sysRoleDTO = SysRoleDTO.convert(role,sysAuthorityDTOS);

        return sysRoleDTO;
    }

    public SysRole findById(Long id) {

        SysRole role = sysRoleMapper.findByIsDeletedIsFalseAndId(id);


        return role;
    }

    public void update(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    public void deleteRole(SysRole sysRole) {

            sysRoleMapper.deleteRole(sysRole);
    }

    public Page<SysRole> query(String q,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<SysRole> sysRoles = sysRoleMapper.query(q);

        return sysRoles;
    }
}
