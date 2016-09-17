package com.example.android.newsapp.Class;

/**
 * Created by samuelhavard on 9/17/16.
 */
public class News {
    private String mTitle;
    private String mBody;

    News(String title, String body) {
        this.mTitle = title;
        this.mBody = body;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getmBody() {
        return mBody;
    }
}
