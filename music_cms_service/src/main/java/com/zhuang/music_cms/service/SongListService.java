package com.zhuang.music_cms.service;

import com.github.pagehelper.PageInfo;
import com.zhuang.music_cms.model.entity.Song;
import com.zhuang.music_cms.model.entity.SongList;
import com.zhuang.music_cms.model.vo.RequestSongList;

import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.service
 * @InterfaceName : SongListService
 * @Description   : 歌单Service接口
 * @Author        : Zhuang
 * @Date          : 2020-05-09 0:50
 */
public interface SongListService {

    public PageInfo<SongList> getSongListAll(int pageCode, int pageSize, String search);

    public void addSongList(RequestSongList requestSongList) throws Exception;

    public SongList getSongListById(Integer id);

    public List<Integer> getSongIdBySongListId(Integer id);

    public void updateSongList(RequestSongList requestSongList) throws Exception;

    public void deleteSongList(Integer id) throws Exception;

    public List<Song> getSongBySongListId(Integer id);

}
