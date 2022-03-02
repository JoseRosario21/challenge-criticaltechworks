package com.jrosario.challenge.criticaltechworks.network;

import android.content.Context;

import com.jrosario.challenge.criticaltechworks.BuildConfig;
import com.jrosario.challenge.criticaltechworks.R;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;

public class APIData {
    public static Promise<ArticleResponse, Throwable, ?> getNewsHeadlines(Context context, TopHeadlinesRequest topHeadlinesRequest) {
        DeferredObject<ArticleResponse, Throwable, ?> deferredObject = new DeferredObject<>();

        NewsApiClient newsApiClient = new NewsApiClient(context.getString(R.string.news_api_key));

        newsApiClient.getTopHeadlines(topHeadlinesRequest,
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        deferredObject.resolve(response);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        deferredObject.reject(throwable);
                    }
                }
        );

        return deferredObject;
    }
}
