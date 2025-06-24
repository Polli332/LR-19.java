package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    private static final String RSS_URL = "https://holdingbkm.com/news/znaynashe-mesyats-promyshlennogo-turizma-startoval-v-bkm-kholding/" +
            ""; // можно изменить на свой

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new NewsAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new FetchFeedTask().execute(RSS_URL);
    }

    private class FetchFeedTask extends AsyncTask<String, Void, List<NewsItem>> {
        @Override
        protected List<NewsItem> doInBackground(String... urls) {
            List<NewsItem> newsItems = new ArrayList<>();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                String title = null, link = null, pubDate = null;
                boolean insideItem = false;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name = parser.getName();
                    if (eventType == XmlPullParser.START_TAG) {
                        if (name.equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (insideItem) {
                            if (name.equalsIgnoreCase("title")) {
                                title = parser.nextText();
                            } else if (name.equalsIgnoreCase("link")) {
                                link = parser.nextText();
                            } else if (name.equalsIgnoreCase("pubDate")) {
                                pubDate = parser.nextText();
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && name.equalsIgnoreCase("item")) {
                        newsItems.add(new NewsItem(title, link, pubDate));
                        insideItem = false;
                    }
                    eventType = parser.next();
                }

                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return newsItems;
        }

        @Override
        protected void onPostExecute(List<NewsItem> items) {
            if (items.isEmpty()) {
                Toast.makeText(MainActivity.this, "Не удалось загрузить новости", Toast.LENGTH_SHORT).show();
            }
            adapter.setNewsList(items);
        }
    }
}
