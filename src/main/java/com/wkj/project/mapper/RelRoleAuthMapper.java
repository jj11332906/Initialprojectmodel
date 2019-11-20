package com.wkj.project.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RelRoleAuthMapper {

    List<Map> findRelRoleAuthsByRoleIds(@Param("roleIds") String roleIds);

    List<Map> findRelRoleAuthsByUsername(@Param("username") String username);

}
