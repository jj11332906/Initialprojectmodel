package com.wkj.project.service;

import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.entity.SysAuthority;
import com.wkj.project.sysStartExec.GlobalVariable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthorityService {



    public List<SysAuthorityDTO> getChildAuthorityByParent(SysAuthority parentAuth) {
        List<SysAuthorityDTO> retObj = new ArrayList<>();
        retObj.add(SysAuthorityDTO.convert(parentAuth));
        List<SysAuthority> childs = parentAuth.getChildren();
        if(childs==null||childs.isEmpty()){
            return retObj;
        }else {
            for (SysAuthority child : childs) {
                retObj.addAll( getChildAuthorityByParent(child));

            }
            return retObj;
        }

    }

    public SysAuthorityDTO findAuthorityDTOByAuthorityCode(String authorityCode) {
        Map<String,SysAuthority> authorityMap = GlobalVariable.sysAuthorityMap;
        SysAuthority authority = authorityMap.get(authorityCode);
        SysAuthorityDTO retObj = SysAuthorityDTO.convert(authority);
        return retObj;
    }

}
