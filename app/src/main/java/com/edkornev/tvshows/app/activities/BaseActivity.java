package com.edkornev.tvshows.app.activities;

import android.support.v7.app.AppCompatActivity;
import com.edkornev.tvshows.app.BaseApplication;

/**
 * Created by Eduard on 16.05.2016.
 */
public class BaseActivity extends AppCompatActivity {

    public BaseApplication getBaseApplication() {
        return (BaseApplication) getApplication();
    }
}
