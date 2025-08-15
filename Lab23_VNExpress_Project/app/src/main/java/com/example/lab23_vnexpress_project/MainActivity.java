package com.example.lab23_vnexpress_project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private MyArrayAdapter adapter;
    private List<List> items = new ArrayList<>();

    private final List<String> FEEDS = Arrays.asList(
            "https://vnexpress.net/rss/tin-moi-nhat.rss",
            "https://vnexpress.net/rss/thoi-su.rss",
            "https://vnexpress.net/rss/the-gioi.rss",
            "https://vnexpress.net/rss/kinh-doanh.rss",
            "https://vnexpress.net/rss/the-thao.rss",
            "https://www.w3schools.com/xml/simple.xml" // fallback RSS
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lvNews);
        adapter = new MyArrayAdapter(this, R.layout.layout_listview, items);
        lv.setAdapter(adapter);

        new LoadRssTask().execute();
    }

    private class LoadRssTask extends AsyncTask<Void, Void, Result> {

        private String errMsg = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            items.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "🔄 Đang tải dữ liệu RSS…", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Result doInBackground(Void... voids) {
            XMLParser fetcher = new XMLParser();
            for (String url : FEEDS) {
                List<List> result;
                try {
                    result = fetcher.fetch(url);
                } catch (Exception e) {
                    result = new ArrayList<>();
                }
                if (!result.isEmpty()) {
                    return new Result(url, result);
                }
            }
            errMsg = "❌ Không lấy được dữ liệu từ các kênh VNExpress!";
            return new Result(FEEDS.get(FEEDS.size() - 1), new ArrayList<>());
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (errMsg != null && !errMsg.isEmpty()) {
                Toast.makeText(MainActivity.this, errMsg, Toast.LENGTH_LONG).show();
            }

            items.addAll(result.data);
            adapter.notifyDataSetChanged();

            String message;
            if (!result.data.isEmpty()) {
                message = "✅ Tải thành công từ:\n" + result.sourceUrl + "\n(" + result.data.size() + " bài viết)";
            } else {
                message = "⚠️ Không có bài viết nào.";
            }
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    // Helper class để thay thế Pair<String, List<Article>>
    private static class Result {
        String sourceUrl;
        List<List> data;

        Result(String sourceUrl, List<List> data) {
            this.sourceUrl = sourceUrl;
            this.data = data;
        }
    }
}
