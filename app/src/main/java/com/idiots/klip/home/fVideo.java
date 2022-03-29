package com.idiots.klip.home;

public class fVideo {
    String url;
    String title;
    String desc;
    fVideo(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public fVideo(String url, String title, String desc) {
        this.url = url;
        this.title = title;
        this.desc = desc;
    }
}
