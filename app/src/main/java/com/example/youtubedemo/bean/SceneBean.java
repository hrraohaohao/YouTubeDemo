package com.example.youtubedemo.bean;

public class SceneBean {

    private String title;
    private STATUS status;

    public SceneBean(String title, STATUS status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
