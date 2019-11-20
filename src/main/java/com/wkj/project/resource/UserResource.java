package com.wkj.project.resource;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wkj.project.mapper.SysUserMapper;
import com.wkj.project.util.ErrorCode;
import com.wkj.project.util.HttpRequestUtil;
import com.wkj.project.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(description = "用户登录")
public class UserResource {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    HttpRequestUtil httpRequest;
    @Autowired
    TokenStore tokenStore;

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
        map.put("user",user);

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
        String url = "http://localhost:8095/oauth/token?username=" + username + "&password=" + password + "&grant_type=password&scope=select&client_id=client_2&client_secret=123456";

        String resultStr = httpRequest.doPost(url, "");
        JSONObject data = JSONUtil.parseObj(resultStr);
        if (data.get("access_token") == null) {
            return Result.getResult(ErrorCode.PWD_NOT_MATCH);
        }

        return Result.getResult(ErrorCode.LOGIN_SUCCESS, data);
    }

}
