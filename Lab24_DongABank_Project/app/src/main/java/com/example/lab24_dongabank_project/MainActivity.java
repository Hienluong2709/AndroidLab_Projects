package com.example.lab24_dongabank_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView lvTyGia;
    private TextView txtDate;
    private MyArrayAdapter adapter;
    private List<TyGia> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTyGia = findViewById(R.id.lvTyGia);
        txtDate = findViewById(R.id.txtdate);

        adapter = new MyArrayAdapter(this, R.layout.item, items);
        lvTyGia.setAdapter(adapter);

        showToday();
        loadData();
    }

    private void showToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        txtDate.setText("Hôm Nay: " + fmt.format(calendar.getTime()));
    }

    private void loadData() {
        new FetchRatesTask().execute();
    }

    private class FetchRatesTask extends AsyncTask<Void, Void, List<TyGia>> {
        @Override
        protected List<TyGia> doInBackground(Void... voids) {
            List<TyGia> result = new ArrayList<>();
            String[] endpoints = {
                    "https://dongabank.com.vn/exchange/export/",
                    "http://dongabank.com.vn/exchange/export/"
            };

            String rawText = null;
            for (String url : endpoints) {
                rawText = downloadText(url);
                if (rawText != null && !rawText.isEmpty()) break;
            }

            if (rawText == null || rawText.isEmpty()) return result;

            int start = rawText.indexOf('{');
            int end = rawText.lastIndexOf('}');
            if (start == -1 || end <= start) return result;

            String jsonText = rawText.substring(start, end + 1);

            try {
                JSONObject root = new JSONObject(jsonText);
                JSONArray arr = root.optJSONArray("items");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject item = arr.getJSONObject(i);
                        TyGia tg = new TyGia();
                        tg.setType(item.optString("type"));
                        tg.setImageurl(item.optString("imageurl"));
                        tg.setMuatienmat(item.optString("muatienmat"));
                        tg.setMuack(item.optString("muack"));
                        tg.setBantienmat(item.optString("bantienmat"));
                        tg.setBanck(item.optString("banck"));
                        tg.setBank(item.optString("bank"));

                        if (tg.getImageurl() != null && !tg.getImageurl().isEmpty()) {
                            tg.setBitmap(downloadBitmap(tg.getImageurl()));
                        }
                        result.add(tg);
                    }
                }
            } catch (Exception e) {
                Log.e("TyGiaDongABank", "Parse JSON error: " + e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<TyGia> tyGias) {
            if (tyGias.isEmpty()) {
                // Fallback tạm thời nếu không lấy được dữ liệu
                TyGia usd = new TyGia();
                usd.setType("USD");
                usd.setMuatienmat("23300");
                usd.setMuack("23370");
                usd.setBantienmat("23600");
                usd.setBanck("23640");
                usd.setBank("DONGA");

                TyGia eur = new TyGia();
                eur.setType("EUR");
                eur.setMuatienmat("25300");
                eur.setMuack("25350");
                eur.setBantienmat("25800");
                eur.setBanck("25850");
                eur.setBank("DONGA");

                adapter.replaceAll(new ArrayList<TyGia>() {{
                    add(usd);
                    add(eur);
                }});
            } else {
                adapter.replaceAll(tyGias);
            }
        }

        private String downloadText(String urlStr) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Android) TyGiaDongABank");
                conn.setRequestProperty("Accept", "*/*");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                reader.close();
                conn.disconnect();
                return sb.toString();
            } catch (Exception e) {
                Log.w("TyGiaDongABank", "downloadText failed: " + e.getMessage());
                return null;
            }
        }

        private Bitmap downloadBitmap(String link) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "*/*");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());
                conn.disconnect();
                return bmp;
            } catch (Exception e) {
                Log.w("TyGiaDongABank", "downloadBitmap failed: " + e.getMessage());
                return null;
            }
        }
    }
}
