package com.zhuang.music_cms.service;

import com.github.pagehelper.PageInfo;
import com.zhuang.music_cms.model.entity.Song;
import com.zhuang.music_cms.model.vo.ResponseSong;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Package       : com.zhuang.music_cms.service
 * @InterfaceName : SongService 
 * @Description   : 歌曲Service接口
 * @Author        : Zhuang
 * @Date          : 2020-05-07 4:34
 */
public interface SongService {

    public PageInfo<Song> getSongAll(int pageCode, int pageSize, String search);

    public Song getSongById(Integer id);

    public void addSong(Song song) throws Exception;

    public void upload(Integer id, MultipartFile songFile, HttpSession session) throws Exception;

    public void updateSong(Song song) throws Exception;

    public void deleteSong(Integer id) throws Exception;

    public List<ResponseSong> getSong();

}
