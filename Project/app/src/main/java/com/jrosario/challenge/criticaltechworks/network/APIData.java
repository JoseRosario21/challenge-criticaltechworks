package com.jrosario.challenge.criticaltechworks.network;

import com.google.gson.JsonObject;
import com.jrosario.challenge.criticaltechworks.models.Article;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;

import java.util.ArrayList;
import java.util.HashMap;

public class APIData {
    public static Promise<ArrayList<Article>, String, ?> getNewsHeadlines(HashMap<String, String> params) {
        DeferredObject<ArrayList<Article>, String, ?> deferredObject = new DeferredObject<>();

        NewsTask.getTopHeadlines(params, new Callback<JsonObject>() {
            @Override
            public void returnResult(JsonObject result) {
                JSONFilesManager.getArticlesFromJSON(result)
                        .done(deferredObject::resolve)
                        .fail(deferredObject::reject);
            }

            @Override
            public void returnError(String message) {
                deferredObject.reject(message);
            }
        });

        return deferredObject;
    }
}
