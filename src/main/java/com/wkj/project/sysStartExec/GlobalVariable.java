package com.wkj.project.sysStartExec;

import com.wkj.project.entity.SysAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Order(1)
public class GlobalVariable implements ApplicationRunner {

    public static SysAuthority sysAuthority;
    public static Map<String, SysAuthority> sysAuthorityMap;

    public static void initialGlobalVariable() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/authorities.xml");
        sysAuthority = ctx.getBean(SysAuthority.class);
        List<SysAuthority> authoritys = getChildAuthorityByParent(sysAuthority);
        sysAuthorityMap = new HashMap<>();
        for (SysAuthority authority : authoritys) {
            log.info(authority.authority);
            sysAuthorityMap.put(authority.authority, authority);
        }

    }

    public static List<SysAuthority> getChildAuthorityByParent(SysAuthority parentAuth) {
        List<SysAuthority> retObj = new ArrayList<>();
        retObj.add(parentAuth);
        List<SysAuthority> childs = parentAuth.getChildren();
        if (childs == null || childs.isEmpty()) {
            return retObj;
        } else {
            for (SysAuthority child : childs) {
                retObj.addAll(getChildAuthorityByParent(child));

            }
            return retObj;
        }

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("-------------->" + "项目启动，GlobalVaribleInitial");
        initialGlobalVariable();
    }
}
