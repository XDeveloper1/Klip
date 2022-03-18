package com.idiots.klip.account;

public class Udata {
    String username, id, bio, number, gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Udata(String username, String id, String bio, String number, String gender) {
        this.username = username;
        this.id = id;
        this.bio = bio;
        this.number = number;
        this.gender = gender;
    }
}
