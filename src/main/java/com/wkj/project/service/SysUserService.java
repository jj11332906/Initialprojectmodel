package com.wkj.project.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.dto.SysRoleDTO;
import com.wkj.project.dto.SysUserDTO;
import com.wkj.project.entity.RelRoleAuth;
import com.wkj.project.entity.SysRole;
import com.wkj.project.entity.SysUser;
import com.wkj.project.mapper.RelRoleAuthMapper;
import com.wkj.project.mapper.SysRoleMapper;
import com.wkj.project.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    RelRoleAuthMapper relRoleAuthMapper;


    public Long insert(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    public SysUserDTO findUserDTOByIsDeletedIsFalseAndId(Long id) {

        SysUser user = sysUserMapper.findByIsDeletedIsFalseAndId(id);
        SysUserDTO sysUserDTO = convertUserToDTO(user);


        return sysUserDTO;
    }

    public SysUser findUserById(Long id) {

        SysUser user = sysUserMapper.findByIsDeletedIsFalseAndId(id);


        return user;
    }

    public SysUserDTO convertUserToDTO(SysUser user) {


        List<SysRoleDTO> sysRoleDTOS = new ArrayList<>();

        List<SysRole> roles = sysRoleMapper.findByIsDeletedIsFalseAndIsEnabledIsTrueAndUser(user);
        roles.forEach(role -> {
            List<RelRoleAuth> relRoleAuths = relRoleAuthMapper.findRelRoleAuthsByRoleId(role.getId().toString());
            List<SysAuthorityDTO> sysAuthorityDTOS = new ArrayList<>();
            relRoleAuths.forEach(relRoleAuth -> {
                SysAuthorityDTO sa = authorityService.findAuthorityDTOByAuthorityCode(relRoleAuth.getAuthority());
                sysAuthorityDTOS.add(sa);
            });

            SysRoleDTO sysRoleDTO = SysRoleDTO.convert(role,sysAuthorityDTOS);

            sysRoleDTOS.add(sysRoleDTO);
        });

        SysUserDTO sysRoleDTO = SysUserDTO.convert(user,sysRoleDTOS);



        return sysRoleDTO;
    }




    public void update(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    public void delete(SysUser sysUser) {

        sysUserMapper.delete(sysUser);
    }

    public Page<SysUser> query(String queryName,String queryUserName,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<SysUser> sysUsers = sysUserMapper.query(queryName,queryUserName);

        return sysUsers;
    }

    public void deleteRoleRelationByUser(SysUser sysUser) {
    }
}
