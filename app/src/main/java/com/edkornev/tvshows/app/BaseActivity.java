package com.edkornev.tvshows.app;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Eduard on 16.05.2016.
 */
public class BaseActivity extends AppCompatActivity {

    public BaseApplication getBaseApplication() {
        return (BaseApplication) getApplication();
    }
}
