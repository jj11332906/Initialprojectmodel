package com.wkj.project.resource;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.dto.SysMenuDTO;
import com.wkj.project.dto.SysRoleDTO;
import com.wkj.project.dto.SysUserDTO;
import com.wkj.project.entity.SysMenu;
import com.wkj.project.entity.SysUser;
import com.wkj.project.service.Oauth2UtilService;
import com.wkj.project.service.SysMenuService;
import com.wkj.project.service.SysUserService;
import com.wkj.project.util.ErrorCode;
import com.wkj.project.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/menu")
@Slf4j
@Api(description = "菜单登录")
public class SysMenuResource {


    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    Oauth2UtilService oauth2UtilService;
    @Autowired
    SysUserService sysUserService;

    @GetMapping("query")
    @ApiOperation(value =
            "根据关键字查询菜单数据")
    public Result query(
            Integer pageNum, Integer pageSize, String ts, String queryGroupName, String queryMenuName
    ) {
        log.info("根据关键字查询菜单数据");
        log.info("ts: " + ts);
        log.info("queryGroupName: " + queryGroupName);
        log.info("queryMenuName: " + queryMenuName);
        Page<SysMenu> sysMenus = sysMenuService.query(queryGroupName, queryMenuName, pageNum, pageSize);
        List<SysMenuDTO> sysMenuDTOS = new ArrayList<>();
        List<SysMenu> sysMenuList = sysMenus.getResult();
        sysMenuList.forEach(menu -> {
            SysMenuDTO sysMenuDTO = sysMenuService.convertEntityToDTO(menu);
            sysMenuDTOS.add(sysMenuDTO);
        });

        return Result.getResult(ErrorCode.OP_SUCCESS, sysMenuDTOS);
    }

    @GetMapping("totalPage")
    @ApiOperation(value =
            "总页数")
    public Result totalPage(
            Integer pageSize, String ts, String queryGroupName, String queryMenuName
    ) {

        int pageNum = 1;
        Page<SysMenu> sysMenus = sysMenuService.query(queryGroupName, queryMenuName, pageNum, pageSize);
        int pages = sysMenus.getPages();
        return Result.getResult(ErrorCode.OP_SUCCESS, pages);
    }


    @PutMapping("putGroup")
    @ApiOperation(value = "修改分组信息")
    public Result updateGroup(
            String id,
            String sort,
            String groupName,
            String description,
            String relationAuthority

    ) {
        log.info("修改分组信息");
        log.info("id：" + id);
        log.info("sort：" + sort);
        log.info("groupName:" + groupName);
        log.info("description:" + description);

        // TODO 修改用户账号基础信息
        SysMenu sysMenu = sysMenuService.findMenuById(Long.valueOf(id));
        sysMenu.setDescription(description);
        sysMenu.setSort(Long.valueOf(sort));
        sysMenu.setMenuName(groupName);
        sysMenu.setUpdateDate(new Date());
        sysMenu.setRelationAuthority(relationAuthority);
        sysMenuService.update(sysMenu);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysMenu);
    }

    @PutMapping("putMenu")
    @ApiOperation(value = "修改菜单信息")
    public Result updateMenu(
            String id,
            String sort,
            String menuName,
            String menuHref,
            String description,
            String groupId,
            String relationAuthority
    ) {
        log.info("修改菜单信息");
        log.info("id：" + id);
        log.info("sort：" + sort);
        log.info("menuName:" + menuName);
        log.info("menuHref:" + menuHref);
        log.info("description:" + description);
        log.info("groupId:" + groupId);

        // TODO 修改用户账号基础信息
        SysMenu sysMenu = sysMenuService.findMenuById(Long.valueOf(id));
        sysMenu.setDescription(description);
        sysMenu.setSort(Long.valueOf(sort));
        sysMenu.setMenuName(menuName);
        sysMenu.setMenuUrl(menuHref);
        sysMenu.setRelationAuthority(relationAuthority);
        SysMenu group = sysMenuService.findMenuById(Long.valueOf(groupId));
        Assert.notNull(group, "分组数据不能为null");
        sysMenu.setGroupMenuId(Long.valueOf(groupId));
        sysMenu.setUpdateDate(new Date());
        sysMenuService.update(sysMenu);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysMenu);
    }


    @PutMapping("delete")
    @ApiOperation(value = "删除信息")
    public Result delete(
            String menuId
    ) {
        log.info("删除信息");
        log.info("menuId：" + menuId);

        SysMenu sysMenu = sysMenuService.findMenuById(Long.valueOf(menuId));
        if(sysMenu.getIsGroup()){
            List<SysMenu> menuList = sysMenuService.findMenuListByGroupId(sysMenu.getId(),null);
            for (SysMenu subMenu:
                 menuList) {
                sysMenuService.delete(subMenu);
            }
        }
        sysMenuService.delete(sysMenu);

        return Result.getResult(ErrorCode.OP_SUCCESS, sysMenu);
    }

    @GetMapping("find/{id}")
    @ApiOperation(value = "根据id获取信息")
    public Result get(
            @PathVariable @ApiParam("菜单id") Long id
    ) {
        SysMenuDTO entity = sysMenuService.findDTOByIsDeletedIsFalseAndId(id);
        return Result.getResult(ErrorCode.OP_SUCCESS, entity);
    }

    @PostMapping("addGroup")
    @ResponseBody
    @ApiOperation(value = "添加分组")
    public Result addGroup(
            String sort,
            String groupName,
            String description,
            String relationAuthority
    ) {
        log.info("添加分组");
        log.info("sort：" + sort);
        log.info("groupName：" + groupName);
        log.info("description:" + description);

        Assert.notNull(sort, "排序不能为空");
        Assert.notNull(groupName, "分组名不能为空");
        // TODO 修改分组信息
        SysMenu sysMenu = new SysMenu();
        sysMenu.setDescription(description == null ? "" : description);
        sysMenu.setSort(Long.valueOf(sort));
        sysMenu.setMenuName(groupName);
        sysMenu.setRelationAuthority(relationAuthority);
        sysMenu.setIsGroup(true);
        sysMenu.setGroupMenuId(null);
        sysMenu.setMenuUrl(null);
        sysMenu.setBaseInfo();
        sysMenuService.insert(sysMenu);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysMenu);

    }

    @PostMapping("addMenu")
    @ResponseBody
    @ApiOperation(value = "添加菜单")
    public Result addMenu(
            String sort,
            String menuName,
            String menuHref,
            String description,
            String groupId,
            String relationAuthority
    ) {
        log.info("添加菜单");
        log.info("sort：" + sort);
        log.info("menuName：" + menuName);
        log.info("menuHref:" + menuHref);
        log.info("description:" + description);
        log.info("groupId:" + groupId);
        SysMenu group = sysMenuService.findMenuById(Long.valueOf(groupId));
        Assert.notNull(group, "分组数据不能为null");

        Assert.notNull(sort, "排序不能为空");
        Assert.notNull(menuName, "菜单名不能为空");
        // TODO 修改分组信息
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuName(menuName);
        sysMenu.setDescription(description == null ? "" : description);
        sysMenu.setRelationAuthority(relationAuthority);
        sysMenu.setSort(Long.valueOf(sort));
        sysMenu.setMenuUrl(menuHref == null ? "" : menuHref);
        sysMenu.setGroupMenuId(Long.valueOf(groupId));
        sysMenu.setIsGroup(false);
        sysMenu.setBaseInfo();
        sysMenuService.insert(sysMenu);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysMenu);
    }


    @GetMapping("groupList")
    @ResponseBody
    @ApiOperation(value = "获取分组数据")
    public Result groupList(
    ) {
        log.info("获取分组列表数据");
        List<SysMenu> sysMenuList = sysMenuService.groupList(null);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysMenuList);
    }


    @GetMapping("menuData")
    @ResponseBody
    @ApiOperation(value = "获取菜单")
    public Result menuData(
            String accessToken
    ) {

        SysUser sysUser = oauth2UtilService.getUserByToken(accessToken);
        SysUserDTO sysUserDTO = sysUserService.convertUserToDTO(sysUser);
        SysRoleDTO sysRoleDTO = sysUserDTO.getSysRoleDTOS().get(0);
        List<SysAuthorityDTO> authorityDTOS = sysRoleDTO.getAuthorityDTOS();
        List<String> authoritys = new ArrayList<>();

        authorityDTOS.forEach(authorityDTO -> {
            String auth = authorityDTO.getAuthority();
            authoritys.add(auth);
        });

        log.info("获取分组列表数据");
        List<SysMenu> groupList = sysMenuService.groupList(authoritys);

        JSONArray groupArray = new JSONArray();
        for (SysMenu group :
                groupList) {
            JSONObject groupJson = JSONUtil.parseObj(group);
            JSONArray menuJSONArray = new JSONArray();
            List<SysMenu> menuList = sysMenuService.findMenuListByGroupId(group.getId(),authoritys);
            for (SysMenu menu :
                    menuList) {
                JSONObject menuJSON = JSONUtil.parseObj(menu);
                menuJSONArray.add(menuJSON);
            }
            groupJson.put("menu",menuJSONArray);
            groupArray.add(groupJson);
        }
        return Result.getResult(ErrorCode.OP_SUCCESS, groupArray);
    }


}
