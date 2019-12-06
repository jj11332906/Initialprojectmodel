package com.wkj.project.config.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer){
        resourceServerSecurityConfigurer.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/user/login/").permitAll()
//                .antMatchers("/article/**")
                .antMatchers("/article/query").hasAnyAuthority("sysadmin","articleMng","articleRead","articleDelete","articleAdd","articleUpdate")
                .antMatchers("/article/totalPage").hasAnyAuthority("sysadmin","articleMng","articleRead","articleDelete","articleAdd","articleUpdate")
                .antMatchers("/article/put/**").hasAnyAuthority("sysadmin","articleMng","articleUpdate")
                .antMatchers("/article/delete").hasAnyAuthority("sysadmin","articleMng","articleDelete")
                .antMatchers("/article/find/**").hasAnyAuthority("sysadmin","articleMng","articleRead","articleDelete","articleAdd","articleUpdate")
                .antMatchers("/article/add").hasAnyAuthority("sysadmin","articleMng","articleAdd");
//                .anyRequest().authenticated();
//        httpSecurity.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
    }

}
