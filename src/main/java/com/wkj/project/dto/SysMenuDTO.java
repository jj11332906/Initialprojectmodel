package com.wkj.project.dto;

import com.wkj.project.entity.SysMenu;
import com.wkj.project.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuDTO {

    /**
     * id
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 访问地址
     */
    private String menuUrl;
    /**
     * 父级菜单
     */
    private String groupId;
    /**
     * 父级名称
     */
    private String groupName;
    /**
     * 是否为分组
     */
    private Boolean isGroup;

    /**
     * 描述
     */
    private String description;

    /**
     * 顺序
     */
    private Long sort;


    /**
     * 描述
     */
    private String createDateStr;

    /**
     *关联权限
     */
    private String relationAuthority;



    public static SysMenuDTO convert(SysMenu entity,SysMenu group) {
        SysMenuDTO dto = new SysMenuDTO();
        BeanUtils.copyProperties(entity, dto);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setCreateDateStr(sdf.format(entity.getCreateDate()));
        if(!entity.getIsGroup()) {
            dto.setGroupId(group.getId().toString());
            dto.setGroupName(group.getMenuName().toString());
        }else{
            dto.setGroupName("");
            dto.setMenuUrl("");
        }
        return dto;
    }
}
