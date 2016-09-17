package com.example.android.newsapp.Activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.newsapp.Class.News;
import com.example.android.newsapp.Class.NewsLoader;
import com.example.android.newsapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String NEWS_URL = "http://content.guardianapis.com/search?q=debates&api-key=test";
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        Log.i(LOG_TAG, "Load Manager");
        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {

        }
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader");
        return new NewsLoader(this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }
}
