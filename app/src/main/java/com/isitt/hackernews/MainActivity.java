package com.isitt.hackernews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.isitt.hackernews.data.remote.HNService;
import com.isitt.hackernews.utilites.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements StoryAdapter.StoryAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;

    private StoryAdapter mStoryAdapter;
    private HNService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getHNService();

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mStoryAdapter = new StoryAdapter(this, this);

        mRecyclerView = findViewById(R.id.rv_stories_list);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mStoryAdapter);
        mRecyclerView.setHasFixedSize(true);

        showLoading();
        loadNews();
    }

    @Override
    public void onClick(int id) {

    }

    private void showNews() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    public void loadNews() {

        mService.getBestStories().enqueue(new Callback<int[]>() {

            @Override
            public void onResponse(Call<int[]> call, Response<int[]> response) {

                if (response.isSuccessful()) {
                    mStoryAdapter.updateData(response.body());
                    showNews();
                } else {
                    int statusCode = response.code();
                    Log.e(TAG, "Response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<int[]> call, Throwable t) {
                Log.d(TAG, "error loading from API");
            }
        });
    }
}
