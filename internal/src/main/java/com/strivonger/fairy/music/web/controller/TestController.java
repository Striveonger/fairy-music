package com.strivonger.fairy.music.web.controller;

import com.striveonger.common.core.result.Result;
import com.striveonger.common.leaf.core.IDGen;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private IDGen idGen;

    @RequestMapping("/hello")
    public Result hello() {
        log.info("hello");
        log.info("defaultIDGen: {}", idGen.next());
        return Result.success().message("Hello World!").show();
    }
}
