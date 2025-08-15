package com.example.lab23_vnexpress_project;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {

    public List<Article> fetch(String urlStr) {
        String xml = download(urlStr);
        if (xml.isBlank()) return new ArrayList<>();

        List<Article> list = new ArrayList<>();

        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(xml));

            int event = parser.getEventType();
            boolean inItem = false;
            String tag = null;

            String title = "";
            String link = "";
            String desc = "";

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName().toLowerCase();
                        if ("item".equals(tag)) {
                            inItem = true;
                            title = "";
                            link = "";
                            desc = "";
                        }
                        break;

                    case XmlPullParser.TEXT:
                    case XmlPullParser.CDSECT:
                        if (inItem) {
                            String text = parser.getText().trim();
                            if ("title".equals(tag)) {
                                title += text;
                            } else if ("link".equals(tag)) {
                                link += text;
                            } else if ("description".equals(tag)) {
                                desc += text;
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("item".equals(parser.getName().toLowerCase()) && inItem) {
                            String img = extractImage(desc);
                            String summary = stripHtml(desc);
                            list.add(new Article(title, summary, link, img));
                            inItem = false;
                        }
                        tag = null;
                        break;
                }
                event = parser.next();
            }

        } catch (Exception e) {
            Log.e("RssFetcher", "Parse error: " + e.getMessage(), e);
        }

        return list;
    }

    private String download(String urlStr) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "VNExpressRss");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();

        } catch (Exception e) {
            Log.e("XMLParser", "Error downloading RSS: " + e.getMessage(), e);
            return "";
        }
    }

    private String extractImage(String html) {
        Pattern pattern = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String stripHtml(String html) {
        return html.replaceAll("<[^>]+>", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

}
