package com.zhuang.music_cms.mapper;

import com.zhuang.music_cms.model.entity.Song;
import com.zhuang.music_cms.model.entity.SongList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.mapper
 * @InterfaceName : SongListMapper 
 * @Description   : 歌单操作接口
 * @Author        : Zhuang
 * @Date          : 2020-05-09 0:49
 */

@Mapper
public interface SongListMapper {

    public List<SongList> getSongListAll(String search);

    public Integer addSongList(SongList songList);

    public void addSongListSong(@Param("songListId") Integer songListId, @Param("songId") List<Integer> songId);

    public void deleteSongListSong(@Param("songListId") Integer songListId, @Param("songId") List<Integer> songId);

    public SongList getSongListById(Integer id);

    public List<Integer> getSongIdBySongListId(Integer id);

    public void updateSongList(SongList songList);

    public void deleteSongList(Integer id);

    public List<Song> getSongBySongListId(Integer id);

}
