package com.edkornev.tvshows.app.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import com.edkornev.tvshows.app.R;
import com.edkornev.tvshows.app.services.api.models.response.ShowResponse;
import com.google.gson.Gson;

/**
 * Created by Eduard on 18.05.2016.
 */
public class PlayerActivity extends BaseActivity {

    private static final String TAG = PlayerActivity.class.getSimpleName();

    private VideoView mVVShow;
    private ShowResponse mResponse;
    private String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mResponse = new Gson().fromJson(getIntent().getStringExtra("response"), ShowResponse.class);
        mTitle = getIntent().getStringExtra("title");

        setTitle(mTitle + " - " + mResponse.getTitle());

        MediaController controller = new MediaController(this);
        controller.setAnchorView(mVVShow);

        Uri uri = Uri.parse(mResponse.getPath());

        mVVShow = (VideoView) findViewById(R.id.vv_show);
        mVVShow.setKeepScreenOn(true);
        mVVShow.setMediaController(controller);
        mVVShow.setVideoURI(uri);
        mVVShow.start();
    }
}
