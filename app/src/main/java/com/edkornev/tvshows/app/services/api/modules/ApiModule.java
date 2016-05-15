package com.edkornev.tvshows.app.services.api.modules;

import com.edkornev.tvshows.app.services.api.ApiService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by Eduard on 16.05.2016.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    ApiService provideApiService() {
        return new ApiService();
    }
}
