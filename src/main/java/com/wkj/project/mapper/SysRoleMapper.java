package com.wkj.project.mapper;

import com.github.pagehelper.Page;
import com.wkj.project.entity.SysRole;
import com.wkj.project.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    List<SysRole> findAll();

    Long insert(SysRole sysRole);

    SysRole findByIsDeletedIsFalseAndId(@Param("id") Long id);

    void update(SysRole sysRole);

    void deleteRole(SysRole sysRole);

    Page<SysRole> query(@Param("q") String q);

    List<SysRole> findByIsDeletedIsFalseAndIsEnabledIsTrueAndUser(SysUser user);
}
