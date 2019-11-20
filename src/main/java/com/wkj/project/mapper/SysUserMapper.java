package com.wkj.project.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysUserMapper {

    Map findByUsername(@Param("username") String username);

}
