package com.wkj.project.mapper;

import com.wkj.project.entity.RelUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RelUserRoleMapper {

    List<Map> findRelUserRolesByUserId(@Param("userId") Long userId);

    void mulInsert(List<RelUserRole> relUserRoles);

    void deleteRelationByUserId(@Param("userId") Long userId);
}
