package com.edkornev.tvshows.app;

import android.app.Application;
import com.edkornev.tvshows.app.services.api.components.ApiComponent;
import com.edkornev.tvshows.app.services.api.components.DaggerApiComponent;
import com.edkornev.tvshows.app.services.api.modules.ApiModule;

/**
 * Created by Eduard on 16.05.2016.
 */
public class BaseApplication extends Application {

    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
