package com.wkj.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wkj.project.entity.SysUser;
import com.wkj.project.mapper.SysUserMapper;
import com.wkj.project.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class Oauth2UtilService {

    @Autowired
    TokenStore tokenStore;

    @Autowired
    SysUserMapper sysUserMapper;

    public SysUser getUserByToken(
            String access_token
    ) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(oAuth2AccessToken);
        Object principal = oAuth2Authentication.getUserAuthentication().getPrincipal();
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(principal, Map.class);
        String username = map.get("username").toString();
        SysUser user = sysUserMapper.findByUsername(username);
        return user;

    }
}
