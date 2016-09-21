package com.example.android.newsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.newsapp.Class.News;
import com.example.android.newsapp.R;

import java.util.ArrayList;

/**
 * NewsAdapter is an adapter used in a list of type {@link News}.  The adapter sets the news article
 * summary, date it was posted and category of news.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, ArrayList<News> newsData) {
        super(context, 0, newsData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }
        News currentData = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentData.getTitle());

        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.section_name);
        sectionTextView.setText(currentData.getSectionName());

        TextView pubDateTextView = (TextView) listItemView.findViewById(R.id.pub_date);
        String pubDate = (currentData.getWebPubDate()).substring(0, 10);
        pubDateTextView.setText(pubDate);

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_name);
        if (currentData.getHasAuthor()) {
            String[] authorArray = currentData.getAuthor();
            for (int i = 0; i < authorArray.length; i++) {
                authorTextView.setText(authorArray[i] + "\n");
            }
        } else {
            authorTextView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
