package com.isitt.hackernews.utilites;

import com.isitt.hackernews.data.remote.HNService;
import com.isitt.hackernews.data.remote.RetrofitClient;

/**
 * Created by loganisitt on 10/3/17.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://hacker-news.firebaseio.com";

    public static HNService getHNService() {
        return RetrofitClient.getClient(BASE_URL).create(HNService.class);
    }
}