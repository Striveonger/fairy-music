package com.striveonger.music.fairy.sources.bilibili;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.striveonger.common.core.Jackson;
import com.striveonger.common.core.Timepiece;
import com.striveonger.common.core.constant.ResultStatus;
import com.striveonger.common.core.exception.CustomException;
import com.striveonger.common.core.vo.BasicSearchVo;
import com.striveonger.music.fairy.sources.api.Music;
import com.striveonger.music.fairy.sources.api.SearchItem;
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
        return "bilibili";
    }

    // https://www.bilibili.com/v/popular/music

    @Override
    public List<BilibiliPlay> convert(String url) {
        Timepiece timepiece = Timepiece.of("Query Bilibili Music Play");

        return List.of();
    }



    @Override
    public List<SearchItem> search(String keyword, int page) {
        String url = "https://search.bilibili.com/all?keyword=" + keyword;
        if (page > 1) {
            url += "&page=" + page;
        }
        Timepiece timepiece = Timepiece.of("Query Bilibili Music Search");
        String html = HttpUtil.get(url);
        timepiece.keep("Http Request");
        Document document = Jsoup.parse(html);
        timepiece.keep("Html Parse");
        Elements elements = document.select("div.video.i_wrapper.search-all-list > div > div");
        timepiece.keep("Elements Select");
        List<SearchItem> list = elements.stream().map(this::convert).filter(Objects::nonNull).toList();
        timepiece.keep("Convert Result");
        timepiece.show();
        // log.info("Bilibili Music search result: {}", list);
        return list;
    }

    private SearchItem convert(Element element) {
        Element a = element.select(".bili-video-card__wrap > a").first(), img;
        if (a == null || (img = a.select("img").first()) == null) {
            return null;
        }
        SearchItem item = new SearchItem();
        item.setUrl("https:" + a.attr("href"));
        item.setCover("https:" + img.attr("src"));
        item.setTitle(img.attr("alt"));
        item.setType(type());
        return item;
    }
}
