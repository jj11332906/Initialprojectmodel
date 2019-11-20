package com.wkj.project.service;

import com.wkj.project.dto.SysAuthorityDTO;
import com.wkj.project.entity.SysAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

}
