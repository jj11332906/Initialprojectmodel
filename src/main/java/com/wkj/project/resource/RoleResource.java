package com.wkj.project.resource;


import com.github.pagehelper.Page;
import com.wkj.project.dto.SysRoleDTO;
import com.wkj.project.entity.RelRoleAuth;
import com.wkj.project.entity.SysRole;
import com.wkj.project.service.SysRoleAuthService;
import com.wkj.project.service.SysRoleService;
import com.wkj.project.util.ErrorCode;
import com.wkj.project.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/role")
@Slf4j
@Api(description = "角色管理")
public class RoleResource {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleAuthService sysRoleAuthService;

    @GetMapping("findAll")
    @ResponseBody
    @ApiOperation(value = "获取角色数据")
    public Result findAll(
    ) {
        log.info("获取角色数据");
        List<SysRoleDTO> sysRoleDTOS = sysRoleService.findAll();
        return Result.getResult(ErrorCode.OP_SUCCESS, sysRoleDTOS);
    }

    @GetMapping("query")
    @ApiOperation(value =
            "根据关键字查询角色数据")
    public Result query(
            Integer pageNum, Integer pageSize,String ts,String queryRoleName
    ) {

        log.info("根据关键字查询角色数据");
        log.info("ts: "+ts);
        log.info("关键字: " + queryRoleName);
        Page<SysRole> sysRoles = sysRoleService.query(queryRoleName, pageNum, pageSize);

        return Result.getResult(ErrorCode.OP_SUCCESS, sysRoles);
    }

    @GetMapping("totalPage")
    @ApiOperation(value =
            "根据总页数")
    public Result totalPage(
            Integer pageSize,String ts,String queryRoleName
    ) {

        int pageNum = 1;
        log.info("根据关键字查询角色数据");
        log.info("ts: "+ts);
        log.info("关键字: " + queryRoleName);
        Page<SysRole> sysRoles = sysRoleService.query(queryRoleName, pageNum, pageSize);
        int pagenum = sysRoles.getPageNum();
        int pages = sysRoles.getPages();
        return Result.getResult(ErrorCode.OP_SUCCESS, pages);
    }




    @PutMapping("put")
    @ApiOperation(value = "修改角色信息")
    public Result update(
            String roleId,
            String roleName,
            String roleDesc,
            String checkedTags
    ) {
        log.info("修改角色");
        log.info("roleId：" + roleId);
        log.info("roleName：" + roleName);
        log.info("roleDesc：" + roleDesc);
        log.info("checkedTags:" + checkedTags);
        // TODO 修改角色基础信息
        SysRole sysRole = sysRoleService.findById(Long.valueOf(roleId));
        sysRole.setName(roleName);
        sysRole.setDescription(roleDesc);
        sysRoleService.update(sysRole);
        String[] ct = checkedTags.split(",");
        // TODO 删除角色权限
        sysRoleAuthService.deleteRelationByRole(sysRole);

        // TODO 批量插入角色权限关系数据
        List<RelRoleAuth> relRoleAuths = new ArrayList<>();
        for (String auth : ct) {
            RelRoleAuth relRoleAuth = new RelRoleAuth();
            relRoleAuth.setRoleId(sysRole.getId());
            relRoleAuth.setAuthority(auth);
            relRoleAuths.add(relRoleAuth);
        }
        sysRoleAuthService.mulInsert(relRoleAuths);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysRole);
    }

    @PutMapping("deleteRole")
    @ApiOperation(value = "删除角色信息")
    public Result deleteRole(
            String roleId
    ) {
        log.info("删除角色");
        log.info("roleId：" + roleId);
        // TODO 修改角色基础信息
        SysRole sysRole = sysRoleService.findById(Long.valueOf(roleId));

        sysRoleService.deleteRole(sysRole);

        return Result.getResult(ErrorCode.OP_SUCCESS, sysRole);
    }

    @GetMapping("find/{id}")
    @ApiOperation(value = "根据id获取角色信息")
    public Result get(
            @PathVariable @ApiParam("角色id") Long id
    ) {
        SysRoleDTO entity = sysRoleService.findByIsDeletedIsFalseAndId(id);
        return Result.getResult(ErrorCode.OP_SUCCESS, entity);
    }

    @PostMapping("add")
    @ResponseBody
    @ApiOperation(value = "添加角色")
    public Result add(
            String roleName,
            String roleDesc,
            String checkedTags
    ) {
        log.info("添加角色");
        log.info("roleName：" + roleName);
        log.info("roleDesc：" + roleDesc);
        log.info("checkedTags:" + checkedTags);
        SysRole sysRole = new SysRole();
        sysRole.setName(roleName);
        sysRole.setDescription(roleDesc);
        sysRole.setCreateDate(new Date());
        sysRole.setUpdateDate(new Date());
        sysRole.setIsDeleted(false);
        sysRole.setIsEnabled(true);

        sysRoleService.insert(sysRole);
        String[] ct = checkedTags.split(",");
        // TODO 批量插入角色权限关系数据
        List<RelRoleAuth> relRoleAuths = new ArrayList<>();
        for (String auth : ct) {
            RelRoleAuth relRoleAuth = new RelRoleAuth();
            relRoleAuth.setRoleId(sysRole.getId());
            relRoleAuth.setAuthority(auth);
            relRoleAuths.add(relRoleAuth);
        }
        sysRoleAuthService.mulInsert(relRoleAuths);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysRole);
    }

}
