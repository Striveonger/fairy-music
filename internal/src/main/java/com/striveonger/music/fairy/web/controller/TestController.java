package com.striveonger.music.fairy.web.controller;

import cn.hutool.core.lang.Dict;
import com.striveonger.common.core.constant.ResultStatus;
import com.striveonger.common.core.exception.CustomException;
import com.striveonger.common.core.result.Result;
import com.striveonger.common.core.vo.BasicSearchVo;
import com.striveonger.common.leaf.core.IDGen;
import com.striveonger.common.storage.entity.FileEntity;
import com.striveonger.common.storage.service.FileService;
import com.striveonger.common.storage.service.StorageService;
import com.striveonger.common.storage.web.utils.FileStreamUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.striveonger.common.storage.context.Storage.StorageType.FILE;

/**
 * @author Mr.Lee
 * @since 2024-08-27 23:12
 */
@Tag(name = "Test")
@RestController
@RequestMapping("/fairy/music/test")
public class TestController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);

    @Resource
    private IDGen fitIDGen;

    @RequestMapping("/hello")
    public Result hello() {
        log.info("hello");
        return Result.success().message("Hello World!").data(Dict.of("segment", fitIDGen.next("A")).set("snowflake", fitIDGen.next()));
    }
}
