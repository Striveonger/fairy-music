package com.strivonger.free.music.sources.bilibili;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.striveonger.common.core.constant.ResultStatus;
import com.striveonger.common.core.exception.CustomException;
import com.striveonger.common.core.utils.JacksonUtils;
import com.strivonger.free.music.sources.api.Music;
import com.strivonger.free.music.sources.api.SearchItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 22:50
 */
public class BiliMusic implements Music {
    private final Logger log = LoggerFactory.getLogger(BiliMusic.class);

    @Override
    public String type() {
        return "Bilibili";
    }

    @Override
    public String convert(String url, Function<String, Boolean> store) {
        // 1. 请求路径
        String html = HttpUtil.get(url);
        Document document = Jsoup.parse(html);
        Elements scripts = document.select("script");
        ObjectNode root = null;
        for (Element script : scripts) {
            String text = script.html();
            if (text.startsWith("window.__playinfo__=")) {
                root = JacksonUtils.readObjectNode(text.substring(20));
                break;
            }
        }

        // 2. 解析数据
        if (Objects.isNull(root)) {
            log.error("Bilibili Music analyze playinfo error: {}", ResultStatus.ANALYZE_CONTENT_FAIL);
            throw new CustomException(ResultStatus.ANALYZE_CONTENT_FAIL, "Bilibili Music analyze playinfo error");
        }



        // 异步执行存储逻辑



        return "";
    }

    @Override
    public List<SearchItem> search(String keyword) {
        return List.of();
    }
}
