package com.zhuang.music_cms.controller;

import com.zhuang.music_cms.exception.JsonResponse;
import com.zhuang.music_cms.model.entity.Song;
import com.zhuang.music_cms.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Package     : com.zhuang.music_cms.controller
 * @ClassName   : SongController 
 * @Description : 歌曲控制器
 * @Author      : Zhuang
 * @Date        : 2020-05-07 4:36
 */

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    private HttpSession httpSession;

    @GetMapping(value = "/song", name = "API_SONG_GET")
    public JsonResponse getSongAll(int pageCode, int pageSize, String search) {
        return JsonResponse.success(songService.getSongAll(pageCode, pageSize, search));
    }

    @GetMapping(value = "/song/{id}", name = "API_SONG_GET")
    public JsonResponse getSongById(@PathVariable Integer id) {
        return JsonResponse.success(songService.getSongById(id));
    }

    @GetMapping(value = "/song/list", name = "API_SONG_GET")
    public JsonResponse getSong() {
        return JsonResponse.success(songService.getSong());
    }

    @PostMapping(value = "/song", name = "API_SONG_POST")
    public JsonResponse addSong(@Valid Song song) throws Exception {
        songService.addSong(song);
        return JsonResponse.success("添加成功");
    }

    @PostMapping(value = "/upload", name = "API_SONG_POST")
    public JsonResponse upload(Integer id, MultipartFile songFile, HttpSession httpSession) throws Exception {
        this.httpSession = httpSession;
        songService.upload(id, songFile, this.httpSession);
        return JsonResponse.success("上传成功");
    }

    @GetMapping("/percent")
    public JsonResponse getUploadPercent() {
        return JsonResponse.success(this.httpSession == null || this.httpSession.getAttribute("upload_percent") == null ? 0 : (int) this.httpSession.getAttribute("upload_percent"));
    }

    @PostMapping("/reset")
    public void resetPercent() {
        if (this.httpSession != null) {
            this.httpSession.setAttribute("upload_percent", 0);
            this.httpSession = null;
        }
    }

    @PutMapping(value = "/song/{id}", name = "API_SONG_PUT")
    public JsonResponse updateSong(@PathVariable Integer id, @Valid Song song) throws Exception {
        songService.updateSong(song);
        return JsonResponse.success("修改成功");
    }

    @DeleteMapping(value = "/song/{id}", name = "API_SONG_DELETE")
    public JsonResponse deleteSong(@PathVariable Integer id) throws Exception {
        songService.deleteSong(id);
        return JsonResponse.success("删除成功");
    }

}
