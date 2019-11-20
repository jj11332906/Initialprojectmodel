package com.wkj.project.mapper;

import com.wkj.project.entity.RelRoleAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RelRoleAuthMapper {

    List<RelRoleAuth> findRelRoleAuthsByRoleId(@Param("roleId") String roleId);

    List<Map> findRelRoleAuthsByUsername(@Param("username") String username);

}
