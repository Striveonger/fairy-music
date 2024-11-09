package com.striveonger.music.fairy.web.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.striveonger.common.core.result.Result;
import com.striveonger.common.web.utils.ResponseStreamUtils;
import com.striveonger.music.fairy.sources.api.Music;
import com.striveonger.music.fairy.sources.bilibili.BiliMusic;
import com.striveonger.music.fairy.sources.bilibili.BilibiliPlay;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.Lee
 * @since 2024-08-27 23:12
 */
@Tag(name = "Music")
@RestController
public class MusicController {
    private final Logger log = LoggerFactory.getLogger(MusicController.class);

    private final Music<BilibiliPlay> music = new BiliMusic();


    @GetMapping("/v1/fairy/music/search")
    public Result search(String keyword, Integer page) {
        log.info("search: {}, page: {}", keyword, page);
        page = page == null || page < 1 ? 1 : page;
        if (StrUtil.isBlank(keyword)) {
            return Result.success().data(List.of());
        }
        return Result.success().data(music.search(keyword, page));
    }

    @GetMapping("/v1/fairy/music/cover")
    public void cover(String url, HttpServletRequest request, HttpServletResponse response) {
        try (HttpResponse result = HttpRequest.get(url).execute()){
            byte[] bytes = result.bodyBytes();
            ResponseStreamUtils.preview("xx.jpg", request, response, bytes);
        }
    }

    @GetMapping("/v1/fairy/music/playlist")
    public Result playlist(String url) {
        log.info("playlist: {}", url);
        return Result.success().data(music.playlist(url));
    }

    @GetMapping("/v1/fairy/music/play")
    public void play(BilibiliPlay play, HttpServletRequest request, HttpServletResponse response) {
        byte[] bytes = music.play(play);
        ResponseStreamUtils.preview("xx.mp3", request, response, bytes);
    }
}
