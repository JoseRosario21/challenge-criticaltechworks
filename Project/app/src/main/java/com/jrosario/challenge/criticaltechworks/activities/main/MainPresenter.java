package com.jrosario.challenge.criticaltechworks.activities.main;

import android.content.Context;

import com.jrosario.challenge.criticaltechworks.R;
import com.jrosario.challenge.criticaltechworks.network.APIData;

import java.util.HashMap;

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
    public void getNewsHeadlines() {
        HashMap<String, String> params = new HashMap<>();
        params.put("apiKey", context.getString(R.string.news_api_key));
        params.put("sources", context.getString(R.string.news_source_id));

        APIData.getNewsHeadlines(params)
                .done(response -> mView.addArticles(response))
                .fail(mView::apiRequestError);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
