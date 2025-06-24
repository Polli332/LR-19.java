package com.example.android;

import android.os.AsyncTask;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchRssTask extends AsyncTask<Void, Void, List<NewsItem>> {
    private NewsAdapter adapter;

    public FetchRssTask(NewsAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<NewsItem> doInBackground(Void... voids) {
        List<NewsItem> newsList = new ArrayList<>();
        try {
            URL url = new URL("https://bkm0.wordpress.com/?_gl=1*ckoa3h*_gcl_au*MTI3MDA0MjU5NS4xNzQzMTQ5Mjkw/rss"); // Замените на свою RSS-ленту
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, null);

            boolean insideItem = false;
            String title = "", link = "", pubDate = "";

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (insideItem) {
                        if (parser.getName().equalsIgnoreCase("title")) {
                            title = parser.nextText();
                        } else if (parser.getName().equalsIgnoreCase("link")) {
                            link = parser.nextText();
                        } else if (parser.getName().equalsIgnoreCase("pubDate")) {
                            pubDate = parser.nextText();
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                    newsList.add(new NewsItem(title, link, pubDate));
                    insideItem = false;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    protected void onPostExecute(List<NewsItem> newsItems) {
        adapter.setNewsList(newsItems);
    }
}
