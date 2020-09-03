package com.zhuang.music_cms.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package     : com.zhuang.music_cms.model.vo
 * @ClassName   : ResponseRoles 
 * @Description : 用户角色响应类
 * @Author      : Zhuang
 * @Date        : 2020-04-30 21:19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRoles {

    private Integer id;
    private String rolesName;

}
