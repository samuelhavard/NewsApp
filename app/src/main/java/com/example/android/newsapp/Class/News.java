package com.example.android.newsapp.Class;

/**
 * Created by samuelhavard on 9/17/16.
 */
public class News {
    private String mTitle;
    private String mArticleURL;
    private String mSectionName;

    News(String title, String articleURL, String sectionName) {
        mTitle = title;
        mArticleURL = articleURL;
        mSectionName = sectionName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getArticleBody() {
        return mArticleURL;
    }

    public String getSectionName() {
        return mSectionName;
    }
}
