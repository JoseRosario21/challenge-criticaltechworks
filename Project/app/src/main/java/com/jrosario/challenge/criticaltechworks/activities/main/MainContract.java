package com.jrosario.challenge.criticaltechworks.activities.main;

import com.jrosario.challenge.criticaltechworks.models.Article;

import java.util.List;

public interface MainContract {
    interface View {
        void initV();
        void apiRequestError(String error);
        void addArticles(List<Article> articles);
    }

    interface Presenter {
        void initP();
        void getNewsHeadlines();
        void onDestroy();
    }
}
