package com.example.android.newsapp.Class;

/**
 * The {@link News} class contains relevant information on a news article such as the article title,
 * date it was published, category of news the article falls under and the URL to the article on
 * the web.
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

    public String getURL() {
        return mArticleURL;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebPubDate () {
        return mWebPubDate;
    }
}
