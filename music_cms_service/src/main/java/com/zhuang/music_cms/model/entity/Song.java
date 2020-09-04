package com.zhuang.music_cms.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Package     : com.zhuang.music_cms.model.entity
 * @ClassName   : Song 
 * @Description : 歌曲实体类
 * @Author      : Zhuang
 * @Date        : 2020-05-07 4:28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    private Integer id;
    @NotBlank(message = "音乐名不许为空")
    private String songName;
    @NotBlank(message = "歌手名不许为空")
    private String singer;
    @NotBlank(message = "专辑名不许为空")
    private String album;
    private String songUrl;

}
