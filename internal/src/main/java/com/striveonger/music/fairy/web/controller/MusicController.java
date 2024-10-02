package com.striveonger.music.fairy.web.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.striveonger.common.core.result.Result;
import com.striveonger.common.leaf.core.IDGen;
import com.striveonger.music.fairy.sources.api.Music;
import com.striveonger.music.fairy.sources.bilibili.BiliMusic;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.Lee
 * @since 2024-08-27 23:12
 */
@Tag(name = "Test")
@RestController
@RequestMapping("/fairy/music")
public class MusicController {
    private final Logger log = LoggerFactory.getLogger(MusicController.class);

    @Resource
    private IDGen fitIDGen;

    private final Music music = new BiliMusic();

    @GetMapping("/hello")
    public Result hello() {
        log.info("hello");
        return Result.success().message("Hello World!").data(Dict.of("segment", fitIDGen.next("A")).set("snowflake", fitIDGen.next()));
    }


    @GetMapping("/search")
    public Result search(String keyword, Integer page) {
        page = page == null || page < 1 ? 1 : page;
        if (StrUtil.isBlank(keyword)) {
            return Result.success().data(List.of());
        }
        return Result.success().data(music.search(keyword, page));
    }
}
