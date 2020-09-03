package com.zhuang.music_cms.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package     : com.zhuang.music_cms.model.vo
 * @ClassName   : ResponseAuthority 
 * @Description : 用户权限响应类
 * @Author      : Zhuang
 * @Date        : 2020-04-27 16:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAuthority {

    private Integer id;
    private String authorityName;
    private String mark;
    private String type;
    private String icon;
    private Integer superiorId;

}
