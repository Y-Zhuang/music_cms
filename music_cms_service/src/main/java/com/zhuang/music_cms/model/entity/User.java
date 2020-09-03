package com.zhuang.music_cms.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.model.entity
 * @ClassName   : user 
 * @Description : 用户实体类
 * @Author      : Zhuang
 * @Date        : 2020-04-21 20:27
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    @NotBlank(message = "手机号不许为空")
    @Length(min = 11, max = 11, message = "手机号格式不正确")
    private String phoneNumber;
    @NotBlank(message = "用户名不许为空")
    private String userName;
    @Min(value = 0, message = "性别有误")
    @Max(value = 1, message = "性别有误")
    private Integer sex;
    @Min(value = 1, message = "年龄不许小于 1")
    private Integer age;
    @NotBlank(message = "密码不许为空")
    private String password;
    private String userImage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createTime;

}
