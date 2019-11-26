package com.wkj.project.mapper;

import com.wkj.project.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {

    List<SysRole> findAll();

    Long insert(SysRole sysRole);

    SysRole findByIsDeletedIsFalseAndId(@Param("id") Long id);

    void update(SysRole sysRole);

    void deleteRole(SysRole sysRole);
}
