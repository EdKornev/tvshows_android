package com.edkornev.tvshows.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.edkornev.tvshows.app.R;
import com.edkornev.tvshows.app.services.api.models.response.SeasonResponse;
import com.edkornev.tvshows.app.services.api.models.response.ShowResponse;
import com.edkornev.tvshows.app.services.api.models.response.TVShowResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 16.05.2016.
 */
public class TVShowActivity extends BaseActivity {

    private static final String TAG = TVShowActivity.class.getSimpleName();

    private RecyclerView mRVResults;
    private InfoAdapter mAdapter;
    private TVShowResponse mResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);

        mResponse = new Gson().fromJson(getIntent().getStringExtra("response"), TVShowResponse.class);
        mAdapter = new InfoAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRVResults = (RecyclerView) findViewById(R.id.rv_results);
        mRVResults.setLayoutManager(manager);
        mRVResults.setAdapter(mAdapter);
    }

    private class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int DESCRIPTION_TYPE = 0;
        private static final int SHOWS_TYPE = 1;

        private List<ShowViewModel> mResults = new ArrayList<>();

        public InfoAdapter() {
            for (SeasonResponse season : mResponse.getSeasons()) {
                for (ShowResponse show : season.getShows()) {
                    mResults.add(new ShowViewModel(season.getNumber(), show));
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return DESCRIPTION_TYPE;
            } else {
                return SHOWS_TYPE;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == DESCRIPTION_TYPE) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_tv_show_description, parent, false);
                return new DescriptionViewHolder(view);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_tv_show_shows, parent, false);
                return new ShowViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder baseHolder, int position) {
            if (baseHolder instanceof DescriptionViewHolder) {
                DescriptionViewHolder holder = (DescriptionViewHolder) baseHolder;

                holder.mTVTitle.setText(mResponse.getTitle());
                holder.mTVOriginalTitle.setText(mResponse.getOriginalTitle());
                holder.mTVDescription.setText(mResponse.getDescription());
                holder.mTVProducer.setText(mResponse.getProducer());

                StringBuilder builder = new StringBuilder();

                for (String country : mResponse.getCountries()) {
                    builder.append(country).append(" ");
                }

                holder.mTVCountry.setText(builder.toString());
            } else {
                ShowViewHolder holder = (ShowViewHolder) baseHolder;

                position--;

                holder.mTVSeasonNumber.setText(mResults.get(position).getStringNumber());
                holder.mTVShowTitle.setText(mResults.get(position).mShowTitle);
            }
        }

        @Override
        public int getItemCount() {
            return 1 + this.mResults.size();
        }

        private class DescriptionViewHolder extends RecyclerView.ViewHolder {

            protected TextView mTVTitle;
            protected TextView mTVOriginalTitle;
            protected TextView mTVDescription;
            protected TextView mTVProducer;
            protected TextView mTVCountry;

            public DescriptionViewHolder(View view) {
                super(view);

                mTVTitle = (TextView) view.findViewById(R.id.tv_title);
                mTVOriginalTitle = (TextView) view.findViewById(R.id.tv_original_title);
                mTVDescription = (TextView) view.findViewById(R.id.tv_description);
                mTVProducer = (TextView) view.findViewById(R.id.tv_producer);
                mTVCountry = (TextView) view.findViewById(R.id.tv_country);
            }
        }

        private class ShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mTVSeasonNumber;
            private TextView mTVShowTitle;

            public ShowViewHolder(View view) {
                super(view);

                view.setOnClickListener(this);

                mTVSeasonNumber = (TextView) view.findViewById(R.id.tv_number);
                mTVShowTitle = (TextView) view.findViewById(R.id.tv_show_title);
            }

            @Override
            public void onClick(View v) {
                Integer position = getAdapterPosition() - 1;
                Integer season = mResults.get(position).mSeasonNumber - 1;
                Integer show = mResults.get(position).mShowNumber - 1;

                Intent activity = new Intent(TVShowActivity.this, PlayerActivity.class);
                activity.putExtra("response", new Gson().toJson(mResponse.getSeasons().get(season).getShows().get(show)));
                activity.putExtra("title", mResponse.getTitle());
                startActivity(activity);
            }
        }
    }

    private class ShowViewModel {
        protected Integer mSeasonNumber;
        protected Integer mShowNumber;
        protected String mShowTitle;
        protected String mShowPath;

        protected String getStringNumber() {
            return String.format("S%02dE%02d", mSeasonNumber, mShowNumber);
        }

        public ShowViewModel(Integer seasonNumber, ShowResponse showResponse) {
            this.mSeasonNumber = seasonNumber;
            this.mShowNumber = showResponse.getNumber();
            this.mShowTitle = showResponse.getTitle();
            this.mShowPath = showResponse.getPath();
        }
    }
}
