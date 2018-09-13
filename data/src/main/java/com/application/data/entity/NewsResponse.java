package com.application.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("articles")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public class Article{
        @SerializedName("title")
        private String title;

        @SerializedName("description")
        private String description;

        @SerializedName("urlToImage")
        private String urlToImage;

        @SerializedName("publishedAt")
        private String publishedAt;

        @SerializedName("content")
        private String content;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getContent() {
            return content;
        }
    }
}
