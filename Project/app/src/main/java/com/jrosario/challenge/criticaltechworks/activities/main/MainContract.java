package com.jrosario.challenge.criticaltechworks.activities.main;

import com.kwabenaberko.newsapilib.models.Article;

import java.util.List;

public interface MainContract {
    interface View {
        void initV();
        void apiRequestError(Throwable error);
        void addArticles(List<Article> articles);
    }

    interface Presenter {
        void initP();
        void getNewsHeadlines(int page);
        void onDestroy();
    }
}
