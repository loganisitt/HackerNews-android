package com.isitt.hackernews.data.remote;

import com.isitt.hackernews.data.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HNService {

    @GET("/v0/beststories.json")
    Call<int[]> getBestStories();

    @GET("/v0/item/{id}.json")
    Call<Item> getItem(@Path("id") String id);
}
