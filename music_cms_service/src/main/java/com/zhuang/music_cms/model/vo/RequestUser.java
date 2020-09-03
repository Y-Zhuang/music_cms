package com.zhuang.music_cms.model.vo;

import com.zhuang.music_cms.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Package     : com.zhuang.music_cms.model.vo
 * @ClassName   : RequestUser
 * @Description : 添加用户实体类
 * @Author      : Zhuang
 * @Date        : 2020-04-24 18:33
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser extends User {

    private MultipartFile imagesFile;

}
