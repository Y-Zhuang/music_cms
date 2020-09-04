package com.zhuang.music_cms.controller;

import com.zhuang.music_cms.exception.JsonResponse;
import com.zhuang.music_cms.model.vo.RequestSongList;
import com.zhuang.music_cms.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Package     : com.zhuang.music_cms.controller
 * @ClassName   : SongListController 
 * @Description : 歌单控制器
 * @Author      : Zhuang
 * @Date        : 2020-05-09 0:53
 */

@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @GetMapping(value = "/songList", name = "API_SONGLIST_GET")
    public JsonResponse getSongListAll(int pageCode, int pageSize, String search) {
        return JsonResponse.success(songListService.getSongListAll(pageCode, pageSize, search));
    }

    @PostMapping(value = "/songList", name = "API_SONGLIST_POST")
    public JsonResponse addSongList(@Valid RequestSongList requestSongList) throws Exception {
        songListService.addSongList(requestSongList);
        System.out.println(requestSongList.getSongId().get(0));
        return JsonResponse.success("添加成功");
    }

    @GetMapping(value = "/songList/{id}", name = "API_SONGLIST_GET")
    public JsonResponse getSongListById(@PathVariable Integer id) {
        return JsonResponse.success(songListService.getSongListById(id));
    }

    @GetMapping(value = "/songList/id/{id}", name = "API_SONGLIST_GET")
    public JsonResponse getSongIdBySongListId(@PathVariable Integer id) {
        return JsonResponse.success(songListService.getSongIdBySongListId(id));
    }

    @PutMapping(value = "/songList/{id}", name = "API_SONGLIST_PUT")
    public JsonResponse updateSongList(@PathVariable String id, @Valid RequestSongList requestSongList) throws Exception {
        songListService.updateSongList(requestSongList);
        return JsonResponse.success("修改成功");
    }

    @DeleteMapping(value = "/songList/{id}", name = "API_SONGLIST_DELETE")
    public JsonResponse deleteSongList(@PathVariable Integer id) throws Exception {
        songListService.deleteSongList(id);
        return JsonResponse.success("删除成功");
    }

    @GetMapping(value = "/songList/song/{id}", name = "API_SONGLIST_GET")
    public JsonResponse getSongBySongListId(@PathVariable Integer id) {
        return JsonResponse.success(songListService.getSongBySongListId(id));
    }
}
