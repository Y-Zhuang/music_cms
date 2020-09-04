package com.zhuang.music_cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuang.music_cms.enums.ExceptionType;
import com.zhuang.music_cms.exception.CustomException;
import com.zhuang.music_cms.mapper.SongListMapper;
import com.zhuang.music_cms.model.entity.Song;
import com.zhuang.music_cms.model.entity.SongList;
import com.zhuang.music_cms.model.vo.RequestSongList;
import com.zhuang.music_cms.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.service.impl
 * @ClassName   : SongListServiceImpl 
 * @Description : 歌单Service实现类
 * @Author      : Zhuang
 * @Date        : 2020-05-09 0:51
 */

@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    @Override
    public PageInfo<SongList> getSongListAll(int pageCode, int pageSize, String search) {
        PageHelper.startPage(pageCode, pageSize);
        List<SongList> list = songListMapper.getSongListAll(search);
        return new PageInfo<SongList>(list);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addSongList(RequestSongList requestSongList) throws Exception {
        try {
            requestSongList.setCreateTime(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
            songListMapper.addSongList(requestSongList);
            if (requestSongList.getSongId() != null && requestSongList.getSongId().size() != 0 && requestSongList.getId() != null) {
                songListMapper.addSongListSong(requestSongList.getId(), requestSongList.getSongId());
            }
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "添加失败");
        }
    }

    @Override
    public SongList getSongListById(Integer id) {
        return songListMapper.getSongListById(id);
    }

    @Override
    public List<Integer> getSongIdBySongListId(Integer id) {
        return songListMapper.getSongIdBySongListId(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateSongList(RequestSongList requestSongList) throws Exception {
        try {
            songListMapper.updateSongList((SongList) requestSongList);
            songListMapper.deleteSongListSong(requestSongList.getId(), requestSongList.getSongId());
            songListMapper.addSongListSong(requestSongList.getId(), requestSongList.getSongId());
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "修改失败");
        }
    }

    @Override
    public void deleteSongList(Integer id) throws Exception {
        try {
            songListMapper.deleteSongList(id);
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "删除失败");
        }
    }

    @Override
    public List<Song> getSongBySongListId(Integer id) {
        return songListMapper.getSongBySongListId(id);
    }

}
