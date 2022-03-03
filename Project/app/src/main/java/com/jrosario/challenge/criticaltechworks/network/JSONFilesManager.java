package com.jrosario.challenge.criticaltechworks.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jrosario.challenge.criticaltechworks.models.Article;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;

import java.util.ArrayList;

public class JSONFilesManager {
    public static Promise<ArrayList<Article>, String, ?> getArticlesFromJSON(JsonObject articlesJson) {
        DeferredObject<ArrayList<Article>, String, ?> deferredObject = new DeferredObject<>();

        ArrayList<Article> articles = new ArrayList<>();

        if (!articlesJson.isJsonNull()){
            if (articlesJson.has("articles") && !articlesJson.get("articles").isJsonNull()) {
                JsonArray articlesArray = articlesJson.getAsJsonArray("articles");
                for (JsonElement articleJson : articlesArray)
                    articles.add(getArticle(articleJson.getAsJsonObject()));
                deferredObject.resolve(articles);
            }
        } else {
            deferredObject.reject(URLConstants.JSON_RESPONSE_ERROR);
        }

        return deferredObject;
    }

    private static Article getArticle(JsonObject jsonObject) {
        Article article;

        String author = "";
        String title = "";
        String description = "";
        String content = "";
        String urlToImage = "";
        String publishedAt = "";

        if (jsonObject.has("author") && !jsonObject.get("author").isJsonNull()) {
            author = jsonObject.get("author").getAsString();
        }

        if (jsonObject.has("title") && !jsonObject.get("title").isJsonNull()) {
            title = jsonObject.get("title").getAsString();
        }

        if (jsonObject.has("description") && !jsonObject.get("description").isJsonNull()) {
            description = jsonObject.get("description").getAsString();
        }

        if (jsonObject.has("content") && !jsonObject.get("content").isJsonNull()) {
            content = jsonObject.get("content").getAsString();
        }

        if (jsonObject.has("urlToImage") && !jsonObject.get("urlToImage").isJsonNull()) {
            urlToImage = jsonObject.get("urlToImage").getAsString();
        }

        if (jsonObject.has("publishedAt") && !jsonObject.get("publishedAt").isJsonNull()) {
            publishedAt = jsonObject.get("publishedAt").getAsString();
        }

        article = new Article(author, title, description, content, urlToImage, publishedAt);
        return article;
    }
}
