package com.jrosario.challenge.criticaltechworks.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
    private final String author;
    private final String title;
    private final String description;
    private final String content;
    private final String urlToImage;
    private final String publishedAt;

    public Article(String author, String title, String description, String content, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.content = content;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }


    protected Article(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        content = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(content);
        parcel.writeString(urlToImage);
        parcel.writeString(publishedAt);
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
