package com.edkornev.tvshows.app.services.api.components;

import com.edkornev.tvshows.app.activities.MainActivity;
import com.edkornev.tvshows.app.services.api.modules.ApiModule;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by Eduard on 16.05.2016.
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(MainActivity mainActivity);
}
