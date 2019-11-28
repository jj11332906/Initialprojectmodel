package com.wkj.project.service;

import com.wkj.project.entity.RelUserRole;
import com.wkj.project.entity.SysUser;
import com.wkj.project.mapper.RelUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelUserRoleService {

    @Autowired
    RelUserRoleMapper relUserRoleMapper;


    public void mulInsert(List<RelUserRole> relUserRoles) {
        relUserRoleMapper.mulInsert(relUserRoles);
    }

    public void deleteRelationByUser(SysUser sysUser) {
        Long userId = sysUser.getId();
        relUserRoleMapper.deleteRelationByUserId(userId);

    }
}
