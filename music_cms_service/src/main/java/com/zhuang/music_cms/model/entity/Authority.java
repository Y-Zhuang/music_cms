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
 * @ClassName   : authority 
 * @Description : 权限实体类
 * @Author      : Zhuang
 * @Date        : 2020-04-26 23:35
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    private Integer id;
    @NotBlank(message = "权限名不许为空")
    private String authorityName;
    @NotBlank(message = "权限说明不许为空")
    private String explanation;
    @NotBlank(message = "权限标识不许为空")
    private String mark;
    @NotBlank(message = "权限类型不许为空")
    private String type;
    private String icon;
    private Integer superiorId;
    private String api;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createTime;

}