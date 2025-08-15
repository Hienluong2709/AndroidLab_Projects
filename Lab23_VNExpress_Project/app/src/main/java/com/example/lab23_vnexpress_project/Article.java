package com.example.lab23_vnexpress_project;

import androidx.annotation.Nullable;

public class Article {
    private String title;
    private String summary;
    private String link;
    @Nullable
    private String imageUrl;

    // Constructor
    public Article(String title, String summary, String link, @Nullable String imageUrl) {
        this.title = title;
        this.summary = summary;
        this.link = link;
        this.imageUrl = imageUrl;
    }

    // Getter và Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
