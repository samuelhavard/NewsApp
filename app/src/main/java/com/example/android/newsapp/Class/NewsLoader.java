package com.example.android.newsapp.Class;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * The {@link NewsLoader} class is a {@link AsyncTaskLoader} used to load the news
 * articles onto the screen
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     *
     * @return a list of {@link News} objects extracted from the web.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return QueryUtils.extractNewsFeed(mUrl);
    }
}
