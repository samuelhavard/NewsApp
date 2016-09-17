package com.example.android.newsapp.Class;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by samuelhavard on 9/17/16.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;
    public static final String LOG_TAG = NewsLoader.class.getName();

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<News> newsData = QueryUtils.extractNewsFeed(mUrl);
        return newsData;
    }
}
