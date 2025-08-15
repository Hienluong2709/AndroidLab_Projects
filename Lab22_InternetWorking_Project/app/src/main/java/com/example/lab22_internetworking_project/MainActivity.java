package com.example.lab22_internetworking_project;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnParse;
    private ListView lv1;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();

    private final String URL_XML = "https://www.w3schools.com/xml/simple.xml";
    private final String TAG = "PizzaXmlParser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnParse);
        lv1 = findViewById(R.id.lv1);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lv1.setAdapter(adapter);

        btnParse.setOnClickListener(v -> {
            if (!isOnline()) {
                Toast.makeText(MainActivity.this, "Thiết bị chưa có Internet", Toast.LENGTH_SHORT).show();
                return;
            }
            new LoadExampleTask().execute();
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
        return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    private class LoadExampleTask extends AsyncTask<Void, Void, List<String>> {

        private String error = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
            Toast.makeText(MainActivity.this, "Đang tải XML…", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> list = new ArrayList<>();
            try {
                String xml = new XMLParser().getXmlFromUrl(URL_XML);
                Log.d(TAG, "Fetched " + xml.length() + " chars: " + (xml.length() > 150 ? xml.substring(0, 150) : xml));
                if (xml.isBlank()) {
                    error = "Không lấy được nội dung từ server";
                    return list;
                }

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new StringReader(xml));

                int eventType = parser.getEventType();
                String currentTag = null;
                String nameValue = "";
                String priceValue = "";

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            currentTag = parser.getName();
                            break;
                        case XmlPullParser.TEXT:
                            String text = parser.getText() != null ? parser.getText().trim() : "";
                            if (!text.isEmpty()) {
                                if ("name".equals(currentTag)) nameValue = text;
                                else if ("price".equals(currentTag)) priceValue = text;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if ("food".equalsIgnoreCase(parser.getName())) {
                                String display = !nameValue.isEmpty() ? nameValue + " - " + priceValue : "(không tên) - " + priceValue;
                                list.add(display);
                                nameValue = "";
                                priceValue = "";
                            }
                            currentTag = null;
                            break;
                    }
                    eventType = parser.next();
                }

                if (list.isEmpty()) error = "Parse xong nhưng không có item nào.";
            } catch (Exception e) {
                error = "Lỗi: " + e.getLocalizedMessage();
                Log.e(TAG, "Parse error", e);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            if (error != null && !error.isEmpty()) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
            }
            adapter.clear();
            adapter.addAll(result);
            Toast.makeText(MainActivity.this, "Hoàn tất: " + result.size() + " dòng", Toast.LENGTH_SHORT).show();
        }
    }
}
