package com.strivonger.free.music.launch;

import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

class BiliMusicApplicationTests {

	@Test
	void contextLoads() {
		String html = HttpUtil.get("https://www.bilibili.com/video/BV11u4y1x71G/");
		Document document = Jsoup.parse(html);
		Elements scripts = document.select("script");
		for (Element script : scripts) {
			String text = script.html();
			System.out.println(text);
		}

	}

}
