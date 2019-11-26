package com.wkj.project.service;

import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.entity.RelRoleAuth;
import com.wkj.project.entity.SysAuthority;
import com.wkj.project.entity.SysRole;
import com.wkj.project.mapper.RelRoleAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleAuthService {

    @Autowired
    RelRoleAuthMapper relRoleAuthMapper;

    public List<SysAuthorityDTO> getChildAuthorityByParent(SysAuthority parentAuth) {
        List<SysAuthorityDTO> retObj = new ArrayList<>();
        retObj.add(SysAuthorityDTO.convert(parentAuth));
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

    public void mulInsert(List<RelRoleAuth> relRoleAuths) {
        relRoleAuthMapper.mulInsert(relRoleAuths);
    }

    public void deleteRelationByRole(SysRole sysRole) {
        Long roleId = sysRole.getId();
        relRoleAuthMapper.deleteRelationByRoleId(roleId);

    }
}
