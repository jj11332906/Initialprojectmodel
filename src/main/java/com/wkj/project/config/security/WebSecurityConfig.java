package com.wkj.project.config.security;

import com.wkj.project.config.cors.CorsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    CorsConfig corsConfig;

    @Value("${wkj.web.demo.url}")
    String webDemoUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()  //定义当需要用户登录时候，转到登录页面
//            .and()
//            .authorizeRequests() //定义哪些URL需要被保护、哪些不需要被保护
//            .anyRequest()   //任何请求、登录后可以访问
//            .authenticated();


//           http.requestMatchers().anyRequest()
//                   .and().authorizeRequests()
//                   .antMatchers("/oauth/*")
//                   .permitAll()
//                   .and().addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////                .jdbcAuthentication().dataSource(dataSource);
////                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//
//        super.configure(auth);
//    }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

//    @Autowired
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(new PasswordEncoder())//在此处应用自定义PasswordEncoder
//                .withUser("user")
//                .password("password")
//                .roles("USER");
//    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsFilter corsFilter() throws Exception {
        return corsConfig.corsFilter();
    }
}
