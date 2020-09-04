package com.zhuang.music_cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuang.music_cms.config.AliyunOSSConfig;
import com.zhuang.music_cms.enums.ExceptionType;
import com.zhuang.music_cms.enums.FileType;
import com.zhuang.music_cms.exception.CustomException;
import com.zhuang.music_cms.mapper.SongMapper;
import com.zhuang.music_cms.model.entity.Song;
import com.zhuang.music_cms.model.vo.ResponseSong;
import com.zhuang.music_cms.service.SongService;
import com.zhuang.music_cms.utils.AliyunOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Package     : com.zhuang.music_cms.service.impl
 * @ClassName   : SongServiceImpl 
 * @Description : 歌曲Service实现类
 * @Author      : Zhuang
 * @Date        : 2020-05-07 4:34
 */

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Override
    public PageInfo<Song> getSongAll(int pageCode, int pageSize, String search) {
        PageHelper.startPage(pageCode, pageSize);
        List<Song> list = songMapper.getSongAll(search);
        return new PageInfo<Song>(list);
    }

    @Override
    public Song getSongById(Integer id) {
        return songMapper.getSongById(id);
    }

    @Override
    public void addSong(Song song) throws Exception {
        try {
            songMapper.addSong(song);
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "添加失败");
        }

    }

    @Override
    public void upload(Integer id, MultipartFile songFile, HttpSession session) throws Exception {
        try {
            String songUrl = aliyunOSSConfig.getDomainApp() + aliyunOSSUtils.uploadFileProgress(songFile, aliyunOSSConfig.getBucketApp(), FileType.VIDEO.getMessage(), session);
            songMapper.updateSongUrl(id, songUrl);
        } catch (Exception e) {
            throw new CustomException(ExceptionType.SYSTEM_ERROR, "上传失败");
        }
    }

    @Override
    public void updateSong(Song song) throws Exception {
        try {
            songMapper.updateSong(song);
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "修改失败");
        }
    }

    @Override
    public void deleteSong(Integer id) throws Exception {
        try {
            Song song = getSongById(id);
            if (song != null) {
                if (song.getSongUrl() != null && !"".equals(song.getSongUrl())) {
                    aliyunOSSUtils.deleteFile(song.getSongUrl().replace(aliyunOSSConfig.getDomainApp(), ""), aliyunOSSConfig.getBucketApp());
                }
                songMapper.deleteSong(id);
            }
        } catch (Exception e) {
            throw new CustomException(ExceptionType.USER_INPUT_ERROR, "删除失败");
        }
    }

    @Override
    public List<ResponseSong> getSong() {
        return songMapper.getSong();
    }
}
