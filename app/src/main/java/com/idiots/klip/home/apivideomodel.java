package com.idiots.klip.home;

public class apivideomodel {
    int id;
    String originalurl;
    public apivideomodel() {


    }


    public apivideomodel(int id, String originalurl) {
        this.id = id;
        this.originalurl = originalurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalurl() {
        return originalurl;
    }

    public void setOriginalurl(String originalurl) {
        this.originalurl = originalurl;
    }
}
