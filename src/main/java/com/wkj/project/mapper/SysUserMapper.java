package com.wkj.project.mapper;

import com.github.pagehelper.Page;
import com.wkj.project.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysUserMapper {

    SysUser findByUsername(@Param("username") String username);

    Long insert(SysUser sysUser);

    SysUser findByIsDeletedIsFalseAndId(@Param("id")  Long id);

    void delete(SysUser sysUser);

    Page<SysUser> query(@Param("queryName") String queryName,@Param("queryUserName")  String queryUserName);

    void update(SysUser sysUser);
}
