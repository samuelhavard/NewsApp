package com.example.android.newsapp.Class;

/**
 * Created by samuelhavard on 9/17/16.
 */
public class News {
    private String mTitle;
    private String mArticleURL;
    private String mSectionName;
    private String mWebPubDate;

    News(String title, String articleURL, String sectionName, String webPubDate) {
        mTitle = title;
        mArticleURL = articleURL;
        mSectionName = sectionName;
        mWebPubDate = webPubDate;
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

    public String getWebPubDate () {
        return mWebPubDate;
    }
}
