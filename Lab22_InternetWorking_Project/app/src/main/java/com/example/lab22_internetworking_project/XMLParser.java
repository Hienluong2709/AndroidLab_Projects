package com.example.lab22_internetworking_project;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLParser {

    public String getXmlFromUrl(String urlString) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);

            int code = connection.getResponseCode();
            BufferedReader reader;
            if (code >= 200 && code <= 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            if (code < 200 || code > 299) {
                String bodyPreview = sb.length() > 200 ? sb.substring(0, 200) : sb.toString();
                Log.e("PizzaXmlParser", "HTTP " + code + " từ " + urlString + "; body=" + bodyPreview);
            }

            return sb.toString();

        } catch (Exception e) {
            Log.e("PizzaXmlParser", "HTTP error: " + e.getLocalizedMessage(), e);
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
