package com.zhuang.music_cms.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package     : com.zhuang.music_cms.model.vo
 * @ClassName   : ResponseSong 
 * @Description : 歌曲响应类
 * @Author      : Zhuang
 * @Date        : 2020-05-09 3:15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSong {

    private Integer id;
    private String songName;

}
