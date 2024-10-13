package com.striveonger.music.fairy.sources.bilibili;

import com.striveonger.music.fairy.sources.api.Play;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mr.Lee
 * @since 2024-10-13 16:48
 */
public class BilibiliPlay extends Play {

    private String bvid;
    private String aid;
    private String cid;
    private String title;

    public BilibiliPlay() {
        this.type = "bilibili";
    }

    public BilibiliPlay(String bvid, String aid, String cid, String title) {
        this.bvid = bvid;
        this.aid = aid;
        this.cid = cid;
        this.title = title;
        this.type = "bilibili";
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
