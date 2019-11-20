package com.wkj.project.config.security;

import com.wkj.project.entity.SysAuthority;
import com.wkj.project.mapper.RelRoleAuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置用户认证逻辑
 */


@Component
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    RelRoleAuthMapper relRoleAuthMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Map> userInfo = relRoleAuthMapper.findRelRoleAuthsByUsername(username);
        String password = null;
        List<SimpleGrantedAuthority> authorityList = new ArrayList<SimpleGrantedAuthority>();
        if(userInfo == null || userInfo.size() == 0){
            throw new UsernameNotFoundException("账号不存在");
        }else{
            password = userInfo.get(0).get("password").toString();
            userInfo.forEach(item -> {
                String authority = item.get("authority").toString();
                authorityList.add(new SimpleGrantedAuthority(authority));
            });

        }
        return new User(username,password,authorityList);
    }
}
