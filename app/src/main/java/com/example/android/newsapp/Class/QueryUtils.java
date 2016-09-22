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
 * {@link QueryUtils} is a utility class that converts a {@link String} into a {@link URL}, makes
 * the web call, prepares and finally parses the results of that web call.
 */
public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * extractNewsFeed is a helper method displayed to the public to take a {@link String} as a URL and
     * return a {@link List} of {@link News}
     *
     * @param url as a {@link String} passed to the helper method makeUrl
     * @return a {@link List} of {@link News} objects to be displayed on the screen
     */
    public static List<News> extractNewsFeed(String url) {
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpConnection(makeUrl(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractNewsFromJson(jsonResponse);
    }

    /**
     * makeUrl is a helper method used to convert a string into a usable {@link URL}
     *
     * @param urlString is a {@link String} to be converted to a {@link URL}
     * @return a {@link URL} to be used in network calls
     */
    private static URL makeUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * makeHttpConnection is a helper method used to make the http call to the web and send the
     * web response to readFromInputStream to be converted from an {@link InputStream} to a
     * {@link String} that can be parsed.
     *
     * @param url is a {@link URL} that is used to make network calls
     * @return a JSON response from the web as a {@link String}
     * @throws IOException
     */
    private static String makeHttpConnection(URL url) throws IOException {
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

    /**
     * readFromInputStream is a helper method used to convert the {@link InputStream} into a
     * {@link StringBuilder} and finally returned as a string to be used later.
     *
     * @param inputStream is an {@link InputStream} from the helper method makeHttpConnection that
     *                    had been returned from the web call.
     * @return a {@link String}
     * @throws IOException
     */
    private static String readFromInputStream(InputStream inputStream) throws IOException {
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

    /**
     * extractNewsFromJson is a helper method used to parse usable data from the web response.
     *
     * @param newsJsonData is a web JSON response in the form of a {@link String} that is to be
     *                     parsed.
     * @return a {@link List} of {@link News}
     */
    private static List<News> extractNewsFromJson(String newsJsonData) {
        List<News> news = new ArrayList<>();

        if (TextUtils.isEmpty(newsJsonData)) {
            return null;
        }

        try {
            JSONObject newsBaseObject = new JSONObject(newsJsonData);
            JSONObject responseObject = newsBaseObject.optJSONObject("response");
            JSONArray newsArray = responseObject.optJSONArray("results");

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject arrayObject = newsArray.getJSONObject(i);
                String webTitle = arrayObject.getString("webTitle");
                String articleURL = arrayObject.getString("webUrl");
                String sectionName = arrayObject.getString("sectionName");
                String webPubDate = arrayObject.getString("webPublicationDate");

                if (arrayObject.has("tags")) {
                    JSONArray tagArray = arrayObject.getJSONArray("tags");
                    String[] author = new String[tagArray.length()];

                    for (int j = 0; j < tagArray.length(); j++) {
                        JSONObject tagObject = tagArray.getJSONObject(j);
                        author[j] = tagObject.getString("webTitle");
                    }
                    news.add(new News(webTitle, articleURL, sectionName, webPubDate, author));
                } else {
                    news.add(new News(webTitle, articleURL, sectionName, webPubDate));
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON data", e);
        }
        return news;
    }
}
