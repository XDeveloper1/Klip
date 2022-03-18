package com.idiots.klip.klip;

public class udata {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String title;
    String desc;

    public udata(String title, String desc, String url) {
        this.title = title;
        this.desc = desc;
        this.url = url;
    }

    String url;
}
