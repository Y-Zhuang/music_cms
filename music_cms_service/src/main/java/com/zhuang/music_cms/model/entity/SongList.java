package com.zhuang.music_cms.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Package     : com.zhuang.music_cms.model.entity
 * @ClassName   : SongList
 * @Description : 歌单实体类
 * @Author      : Zhuang
 * @Date        : 2020-05-09 0:47
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongList {

    private Integer id;
    @NotBlank(message = "歌单名不许为空")
    private String songListName;
    @NotBlank(message = "简介不许为空")
    private String introduction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createTime;
}
