package com.edkornev.tvshows.app.services.api.models.response;

/**
 * Created by Eduard on 16.05.2016.
 */
public class BaseResponse<T> {

    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
