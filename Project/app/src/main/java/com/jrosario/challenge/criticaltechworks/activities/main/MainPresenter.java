package com.jrosario.challenge.criticaltechworks.activities.main;

import android.content.Context;

import com.jrosario.challenge.criticaltechworks.network.APIData;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private final Context context;

    public MainPresenter(Context context, MainContract.View view) {
        this.context = context;
        this.mView = view;
    }

    @Override
    public void initP() {
        mView.initV();
    }

    @Override
    public void getNewsHeadlines(int page) {
        TopHeadlinesRequest topHeadlinesRequest = new TopHeadlinesRequest.Builder()
                .sources("bbc-news")
                .pageSize(2)
                .page(page)
                .build();

        APIData.getNewsHeadlines(context, topHeadlinesRequest)
                .done(response -> mView.addArticles(response.getArticles()))
                .fail(mView::apiRequestError);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
