package com.wkj.project.resource;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.wkj.project.dto.SysUserDTO;
import com.wkj.project.entity.RelUserRole;
import com.wkj.project.entity.SysUser;
import com.wkj.project.mapper.SysUserMapper;
import com.wkj.project.service.RelUserRoleService;
import com.wkj.project.service.SysUserService;
import com.wkj.project.util.ErrorCode;
import com.wkj.project.util.HttpRequestUtil;
import com.wkj.project.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(description = "用户管理")
public class SysUserResource {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    HttpRequestUtil httpRequest;
    @Autowired
    TokenStore tokenStore;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    RelUserRoleService relUserRoleService;

    @GetMapping("user")
    @ResponseBody
    @ApiOperation(value = "获取用户信息")
    public Result getUsers(
            String access_token
    ) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
//        OAuth2RefreshToken oAuth2RefreshToken = tokenStore.readRefreshToken(access_token);
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(oAuth2AccessToken);
        Object principal = oAuth2Authentication.getUserAuthentication().getPrincipal();
//        User userPrincipal = (User)principal;
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(principal, Map.class);
        String username = map.get("username").toString();
        Map user = sysUserMapper.findByUsername(username);
        map.put("user", user);

        return Result.getResult(map);

    }


    @PostMapping("login")
    @ResponseBody
    @ApiOperation(value = "登录")
    public Result post(
            String username,
            String password
    ) {
        log.info("登录");
        Map user = sysUserMapper.findByUsername(username);

        if (user == null) {
            return Result.getResult(ErrorCode.NOT_FUND_USER);
        }
        String url = "http://127.0.0.1:8095/oauth/token?username=" + username + "&password=" + password + "&grant_type=password&scope=select&client_id=client_2&client_secret=123456";

        String resultStr = httpRequest.doPost(url, "");
        JSONObject data = JSONUtil.parseObj(resultStr);
        if (data.get("access_token") == null) {
            return Result.getResult(ErrorCode.PWD_NOT_MATCH);
        }

        return Result.getResult(ErrorCode.LOGIN_SUCCESS, data);
    }


    @GetMapping("query")
    @ApiOperation(value =
            "根据关键字查询用户账号数据")
    public Result query(
            Integer pageNum, Integer pageSize, String ts, String queryName, String queryUserName
    ) {
        log.info("根据关键字查询用户数据");
        log.info("ts: " + ts);
        log.info("queryName: " + queryName);
        log.info("queryUserName: " + queryUserName);
        Page<SysUser> sysUsers = sysUserService.query(queryName, queryUserName, pageNum, pageSize);
        List<SysUserDTO> sysUserDTOS = new ArrayList<>();
        List<SysUser> sysUserList = sysUsers.getResult();
        sysUserList.forEach(user -> {
            SysUserDTO sysUserDTO = sysUserService.convertUserToDTO(user);
            sysUserDTOS.add(sysUserDTO);
        });

        return Result.getResult(ErrorCode.OP_SUCCESS, sysUserDTOS);
    }

    @GetMapping("totalPage")
    @ApiOperation(value =
            "根据总页数")
    public Result totalPage(
            Integer pageSize, String ts, String queryName, String queryUserName
    ) {

        int pageNum = 1;
        Page<SysUser> sysUsers = sysUserService.query(queryName, queryUserName, pageNum, pageSize);
        int pages = sysUsers.getPages();
        return Result.getResult(ErrorCode.OP_SUCCESS, pages);
    }


    @PutMapping("put")
    @ApiOperation(value = "修改用户账号信息")
    public Result update(
            String id,
            String name,
            String username,
            String email,
            String roleId,
            String describe

    ) {
        log.info("修改角色");
        log.info("roleId：" + roleId);
        log.info("id：" + id);
        log.info("name：" + name);
        log.info("username:" + username);
        log.info("email:" + email);
        log.info("describe:" + describe);
        // TODO 修改用户账号基础信息
        SysUser sysUser = sysUserService.findUserById(Long.valueOf(id));
        sysUser.setDescription(describe);
        sysUser.setName(name);
        sysUser.setEmail(email);
        sysUser.setUsername(username);
        sysUserService.update(sysUser);

        // TODO 删除管理角色
        sysUserService.deleteRoleRelationByUser(sysUser);

        // TODO 批量插入用户角色关系数据
        List<RelUserRole> relUserRoles = new ArrayList<>();
        RelUserRole relUserRole = new RelUserRole();
        relUserRole.setUserId(Long.valueOf(id));
        relUserRole.setRoleId(Long.valueOf(roleId));
        relUserRoles.add(relUserRole);
        relUserRoleService.mulInsert(relUserRoles);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysUser);
    }

    @PutMapping("delete")
    @ApiOperation(value = "删除用户账号信息")
    public Result deleteRole(
            String userId
    ) {
        log.info("删除用户账号");
        log.info("userId：" + userId);
        // TODO 修改用户账号状态信息
        SysUser sysUser = sysUserService.findUserById(Long.valueOf(userId));

        sysUserService.delete(sysUser);

        return Result.getResult(ErrorCode.OP_SUCCESS, sysUser);
    }

    @GetMapping("find/{id}")
    @ApiOperation(value = "根据id获取用户账号信息")
    public Result get(
            @PathVariable @ApiParam("角色id") Long id
    ) {
        SysUserDTO entity = sysUserService.findUserDTOByIsDeletedIsFalseAndId(id);
        return Result.getResult(ErrorCode.OP_SUCCESS, entity);
    }

    @PostMapping("add")
    @ResponseBody
    @ApiOperation(value = "添加用户账号")
    public Result add(
            String name,
            String username,
            String email,
            String roleId,
            String describe
    ) {
        log.info("添加");
        log.info("roleId：" + roleId);
        log.info("name：" + name);
        log.info("username:" + username);
        log.info("email:" + email);
        log.info("describe:" + describe);
        Assert.notNull(username, "账号不能为空");
        // TODO 修改用户账号基础信息
        SysUser sysUser = new SysUser();
        sysUser.setDescription(describe == null ? "" : describe);
        sysUser.setName(name == null ? "" : name);
        sysUser.setPassword(username);
        sysUser.setEmail(email == null ? "" : email);
        sysUser.setUsername(username);
        sysUser.setBaseInfo();
        sysUserService.insert(sysUser);


        // TODO 批量插入用户角色关系数据
        List<RelUserRole> relUserRoles = new ArrayList<>();
        RelUserRole relUserRole = new RelUserRole();
        relUserRole.setUserId(sysUser.getId());
        relUserRole.setRoleId(Long.valueOf(roleId));
        relUserRoles.add(relUserRole);
        relUserRoleService.mulInsert(relUserRoles);
        return Result.getResult(ErrorCode.OP_SUCCESS, sysUser);
    }

}
