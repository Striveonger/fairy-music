package com.striveonger.music.fairy.web.controller;

import com.striveonger.common.core.thread.ThreadKit;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author Mr.Lee
 * @since 2024-08-27 23:12
 */
@Tag(name = "Test")
@RestController
public class TestController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("test/flux")
    public Flux<Long> flux() {
        return Flux.just(1, 3, 2).map(Long::valueOf);
        // return Flux.interval(Duration.ofSeconds(1));
    }

    @GetMapping("test/mono")
    public Mono<String> mono() {
        return Mono.just(getData());
    }

    private String getData() {
        ThreadKit.sleep(100);
        return "abc";
    }
}
