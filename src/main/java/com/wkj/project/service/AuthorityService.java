package com.wkj.project.service;

import com.wkj.project.dto.BootStrapTreeViewAuthorityNodeDTO;
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


    public List<BootStrapTreeViewAuthorityNodeDTO> getBootStrapTreeViewAuthorityNodeByParent(SysAuthority parentAuth) {
        List<BootStrapTreeViewAuthorityNodeDTO> retObj = new ArrayList<>();
        List<SysAuthority> childs = parentAuth.getChildren();
        if(childs!=null){
            for(SysAuthority sysAuthority:childs){
                BootStrapTreeViewAuthorityNodeDTO bootStrapTreeViewAuthorityNodeDTO = new BootStrapTreeViewAuthorityNodeDTO();
                String[] tags = new String[1];
                tags[0] = sysAuthority.getAuthority();
                bootStrapTreeViewAuthorityNodeDTO.setTags(tags);
                bootStrapTreeViewAuthorityNodeDTO.setText(sysAuthority.getDescription());
                List<BootStrapTreeViewAuthorityNodeDTO> bootStrapTreeViewAuthorityChildsNodeDTO = getBootStrapTreeViewAuthorityNodeByParent(sysAuthority);
                if(bootStrapTreeViewAuthorityChildsNodeDTO!=null&&!bootStrapTreeViewAuthorityChildsNodeDTO.isEmpty()) {
                    bootStrapTreeViewAuthorityNodeDTO.setNodes(bootStrapTreeViewAuthorityChildsNodeDTO);
                }
                retObj.add(bootStrapTreeViewAuthorityNodeDTO);
            }
            return retObj;
        }else{
            return null;
        }
    }



    public SysAuthorityDTO findAuthorityDTOByAuthorityCode(String authorityCode) {
        Map<String,SysAuthority> authorityMap = GlobalVariable.sysAuthorityMap;
        SysAuthority authority = authorityMap.get(authorityCode);
        SysAuthorityDTO retObj = SysAuthorityDTO.convert(authority);
        return retObj;
    }

}
