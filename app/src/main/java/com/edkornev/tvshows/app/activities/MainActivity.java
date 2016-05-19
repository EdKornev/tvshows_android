package com.edkornev.tvshows.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.edkornev.tvshows.app.R;
import com.edkornev.tvshows.app.services.api.ApiService;
import com.edkornev.tvshows.app.services.api.models.response.BaseResponse;
import com.edkornev.tvshows.app.services.api.models.response.TVShowResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject ApiService mApiService;

    private RecyclerView mRVResults;
    private TVShowsAdapter mAdapter = new TVShowsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getBaseApplication().getApiComponent().inject(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRVResults = (RecyclerView) findViewById(R.id.rv_results);
        mRVResults.setLayoutManager(manager);
        mRVResults.setAdapter(mAdapter);

        Call<BaseResponse<List<TVShowResponse>>> request = mApiService.getApi().getAllTvShows();
        request.enqueue(callback);
    }

    private Callback<BaseResponse<List<TVShowResponse>>> callback = new Callback<BaseResponse<List<TVShowResponse>>>() {
        @Override
        public void onResponse(Call<BaseResponse<List<TVShowResponse>>> call, Response<BaseResponse<List<TVShowResponse>>> response) {
            if (response.code() == 200) {
                mAdapter.mResults = response.body().getResults();
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<BaseResponse<List<TVShowResponse>>> call, Throwable t) {
            Log.e(TAG, "Error while request get all tv shows", t);
        }
    };

    private class TVShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<TVShowResponse> mResults = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_tv_show, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder baseHolder, int position) {
            ViewHolder holder = (ViewHolder) baseHolder;

            holder.mTVTitle.setText(mResults.get(position).getTitle());
            holder.mTVOriginalTitle.setText(mResults.get(position).getOriginalTitle());
            holder.mTVCountry.setText(formListString(mResults.get(position).getCountries()));
            holder.mTVGenres.setText(formListString(mResults.get(position).getGenres()));

            Picasso.with(MainActivity.this)
                    .load(mApiService.getPictureUrl(mResults.get(position).getPicture()))
                    .into(holder.mIVPicture);
        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }

        private String formListString(List<String> list) {
            StringBuilder builder = new StringBuilder();

            for (String item : list) {
                builder.append(item)
                        .append(" ");
            }

            return builder.toString();
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            protected TextView mTVTitle;
            protected TextView mTVOriginalTitle;
            protected TextView mTVCountry;
            protected TextView mTVGenres;
            protected ImageView mIVPicture;

            public ViewHolder(View view) {
                super(view);

                view.setOnClickListener(this);

                mTVTitle = (TextView) view.findViewById(R.id.tv_title);
                mTVOriginalTitle = (TextView) view.findViewById(R.id.tv_original_title);
                mTVCountry = (TextView) view.findViewById(R.id.tv_country);
                mTVGenres = (TextView) view.findViewById(R.id.tv_genres);
                mIVPicture = (ImageView) view.findViewById(R.id.iv_picture);
            }

            @Override
            public void onClick(View v) {
                Intent activity = new Intent(MainActivity.this, TVShowActivity.class);
                activity.putExtra("response", new Gson().toJson(mResults.get(getAdapterPosition())));

                startActivity(activity);
            }
        }
    }
}
