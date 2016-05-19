package com.edkornev.tvshows.app.services.api;

import com.edkornev.tvshows.app.services.api.models.response.BaseResponse;
import com.edkornev.tvshows.app.services.api.models.response.TVShowResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by Eduard on 15.05.2016.
 */
public class ApiService {

    private static final String DOMAIN = "http://tvshow-edbrown.rhcloud.com";
    private static final String APP = "/serials_server";
    private static final String VERSION = APP + "/api/v1";
    private static final String PICTURE_PATH = DOMAIN + APP + "/pic/";

    private Api mApi;

    public Api getApi() {
        if (mApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = retrofit.create(Api.class);
        }

        return mApi;
    }

    public String getPictureUrl(String id) {
        return PICTURE_PATH + id;
    }

    public interface Api {

        @GET(value = VERSION + "/tv")
        Call<BaseResponse<List<TVShowResponse>>> getAllTvShows();
    }
}
