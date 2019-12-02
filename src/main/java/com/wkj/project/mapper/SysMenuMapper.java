package com.wkj.project.mapper;

import com.github.pagehelper.Page;
import com.wkj.project.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

public interface SysMenuMapper {


    Long insert(SysMenu sysMenu);

    SysMenu findByIsDeletedIsFalseAndId(@Param("id") Long id);

    void delete(SysMenu sysMenu);

    Page<SysMenu> query(@Param("queryGroupName") String queryGroupName, @Param("queryMenuName") String queryMenuName);

    void update(SysMenu sysMenu);
}
