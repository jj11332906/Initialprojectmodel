package com.wkj.project.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RelUserRoleMapper {

    List<Map> findRelUserRolesByUserId(@Param("userId") Long userId);

}
