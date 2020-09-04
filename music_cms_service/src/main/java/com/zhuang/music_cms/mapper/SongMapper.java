package com.zhuang.music_cms.mapper;

import com.zhuang.music_cms.model.entity.Song;
import com.zhuang.music_cms.model.vo.ResponseSong;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.mapper
 * @InterfaceName : SongMapper 
 * @Description   : 音乐操作接口
 * @Author        : Zhuang
 * @Date          : 2020-05-07 4:32
 */

@Mapper
public interface SongMapper {

    public List<Song> getSongAll(String search);

    public Song getSongById(Integer id);

    public void addSong(Song song);

    public void updateSongUrl(@Param("id")Integer id, @Param("songUrl")String songUrl);

    public void updateSong(Song song);

    public void deleteSong(Integer id);

    public List<ResponseSong> getSong();

}
