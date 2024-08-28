package com.strivonger.fairy.music.sources.api;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 21:52
 */
public class SearchItem {

    /**
     * 列表显示内容
     */
    String title;

    /**
     * 封面地址
     */
    String cover;

    /**
     * 播放地址
     */
    String url;


    /**
     * 类型
     */
    String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
