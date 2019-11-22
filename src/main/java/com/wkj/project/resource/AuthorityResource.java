package com.wkj.project.resource;


import com.wkj.project.dto.BootStrapTreeViewAuthorityNodeDTO;
import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.entity.SysAuthority;
import com.wkj.project.service.AuthorityService;
import com.wkj.project.sysStartExec.GlobalVariable;
import com.wkj.project.util.ErrorCode;
import com.wkj.project.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authority")
@Slf4j
@Api(description = "权限管理")
public class AuthorityResource {

    @Autowired
    AuthorityService authorityService;

    @GetMapping("get")
    @ResponseBody
    @ApiOperation(value = "获取权限数据")
    public Result list(
    ) {
        log.info("获取权限数据");
        SysAuthority sysAuthority = GlobalVariable.sysAuthority;
        List<SysAuthorityDTO> sysAuthorityDTOS = authorityService.getChildAuthorityByParent(sysAuthority);


        return Result.getResult(ErrorCode.OP_SUCCESS, sysAuthorityDTOS);
    }

    @GetMapping("getAuthorityTree")
    @ResponseBody
    @ApiOperation(value = "获取权限树数据")
    public Result authorityTree(
    ) {
        log.info("获取权限数据");
        SysAuthority sysAuthority = GlobalVariable.sysAuthority;
        // TODO: 2019/11/22  这里需要将 SysAuthority 转变为 bootstrap-treeView需要的数据结构，参考网址：http://www.jq22.com/jquery-info10461
        BootStrapTreeViewAuthorityNodeDTO bootStrapTreeViewAuthorityNodeDTO = new BootStrapTreeViewAuthorityNodeDTO();
        String[] tags = new String[1];
        tags[0] = sysAuthority.getAuthority();
        bootStrapTreeViewAuthorityNodeDTO.setTags(tags);
        bootStrapTreeViewAuthorityNodeDTO.setText(sysAuthority.getDescription());
        List<BootStrapTreeViewAuthorityNodeDTO> bootStrapTreeViewAuthorityChildsNodeDTO = authorityService.getBootStrapTreeViewAuthorityNodeByParent(sysAuthority);
        if(bootStrapTreeViewAuthorityChildsNodeDTO!=null&&!bootStrapTreeViewAuthorityChildsNodeDTO.isEmpty()){
            bootStrapTreeViewAuthorityNodeDTO.setNodes(bootStrapTreeViewAuthorityChildsNodeDTO);
        }
        return Result.getResult(ErrorCode.OP_SUCCESS, bootStrapTreeViewAuthorityNodeDTO);
    }

}
