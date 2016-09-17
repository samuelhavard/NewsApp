package com.example.android.newsapp.Class;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelhavard on 9/17/16.
 */
public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    QueryUtils() {}

    public static List<News> extractNewsFeed(String url) {
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpConnection(makeUrl(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractNewsFromJson(jsonResponse);
    }

    private static URL makeUrl (String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpConnection(URL url) throws IOException{
        String jsonResponse = "";

        if (url == null) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error Response Code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem Retrieving News", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String lines = reader.readLine();
            while (lines != null) {
                output.append(lines);
                lines = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractNewsFromJson (String newsJsonData) {
        List<News> news = new ArrayList<>();

        if (TextUtils.isEmpty(newsJsonData)) {
            return null;
        }

        try {
            JSONObject newsBaseObject =  new JSONObject(newsJsonData);
            JSONObject responseObject = newsBaseObject.optJSONObject("response");
            JSONArray newsArray = responseObject.optJSONArray("results");

            if (newsArray != null) {
                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject arrayObject = newsArray.getJSONObject(i);
                    //JSONObject propertiesObject = arrayObject.getJSONObject("");

                    String webTitle = arrayObject.getString("webTitle");
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON data", e);
        }
        return news;
    }
}
