package com.zhuang.music_cms.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.model.entity
 * @ClassName   : Roles 
 * @Description : 角色实体类
 * @Author      : Zhuang
 * @Date        : 2020-04-26 23:32
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    private Integer id;
    @NotBlank(message = "角色名不许为空")
    private String rolesName;
    @NotBlank(message = "角色说明不许为空")
    private String explanation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createTime;

}
