package com.edkornev.tvshows.app.services.api.models.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 17.05.2016.
 */
public class SeasonResponse {

    private Integer number;
    private List<ShowResponse> shows = new ArrayList<>();

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<ShowResponse> getShows() {
        return shows;
    }

    public void setShows(List<ShowResponse> shows) {
        this.shows = shows;
    }
}
