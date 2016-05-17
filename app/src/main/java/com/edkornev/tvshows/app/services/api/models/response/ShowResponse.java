package com.edkornev.tvshows.app.services.api.models.response;

/**
 * Created by Eduard on 17.05.2016.
 */
public class ShowResponse {

    private Integer number;
    private String title;
    private String path;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
