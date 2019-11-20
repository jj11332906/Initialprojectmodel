package com.wkj.project.resource;


import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.entity.SysAuthority;
import com.wkj.project.service.AuthorityService;
import com.wkj.project.util.ErrorCode;
import com.wkj.project.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
@Slf4j
@Api(description = "角色管理")
public class RoleResource {

    @Autowired
    AuthorityService authorityService;

    @GetMapping("get")
    @ResponseBody
    @ApiOperation(value = "获取角色数据")
    public Result list(
    ) {
        log.info("获取角色数据");


        return Result.getResult(ErrorCode.OP_SUCCESS, null);
    }

}
