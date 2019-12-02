package com.wkj.project.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.dto.SysMenuDTO;
import com.wkj.project.dto.SysRoleDTO;
import com.wkj.project.dto.SysUserDTO;
import com.wkj.project.entity.RelRoleAuth;
import com.wkj.project.entity.SysMenu;
import com.wkj.project.entity.SysRole;
import com.wkj.project.entity.SysUser;
import com.wkj.project.mapper.RelRoleAuthMapper;
import com.wkj.project.mapper.SysMenuMapper;
import com.wkj.project.mapper.SysRoleMapper;
import com.wkj.project.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    public Long insert(SysMenu sysMenu) {
        return sysMenuMapper.insert(sysMenu);
    }

    public SysMenuDTO findDTOByIsDeletedIsFalseAndId(Long id) {

        SysMenuDTO menuDTO;
        SysMenu entity = sysMenuMapper.findByIsDeletedIsFalseAndId(id);
        menuDTO = convertEntityToDTO(entity);
        return menuDTO;
    }

    public SysMenu findMenuById(Long id) {
        SysMenu menu = sysMenuMapper.findByIsDeletedIsFalseAndId(id);
        return menu;
    }

    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    public void delete(SysMenu sysMenu) {
        sysMenuMapper.delete(sysMenu);
    }

    public Page<SysMenu> query(String queryGroupName,String queryMenuName,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<SysMenu> sysMenus = sysMenuMapper.query(queryGroupName,queryMenuName);

        return sysMenus;
    }

    public SysMenuDTO convertEntityToDTO(SysMenu entity) {
        SysMenuDTO menuDTO;
        Boolean isGroup = entity.getIsGroup();
        if(isGroup) {
            menuDTO = SysMenuDTO.convert(entity,null);
        }else{
            Long groupId = entity.getGroupMenuId();
            SysMenu group =  sysMenuMapper.findByIsDeletedIsFalseAndId(groupId);
            menuDTO =  SysMenuDTO.convert(entity,group);
        }

        return menuDTO;
    }
}
