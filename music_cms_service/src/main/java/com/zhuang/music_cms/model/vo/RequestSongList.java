package com.zhuang.music_cms.model.vo;

import com.zhuang.music_cms.model.entity.SongList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.model.vo
 * @ClassName   : RequestSongList
 * @Description : TODO
 * @Author      : Zhuang
 * @Date        : 2020-05-09 5:02
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSongList extends SongList {

    private List<Integer> songId;

}
