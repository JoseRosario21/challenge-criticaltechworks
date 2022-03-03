package com.jrosario.challenge.criticaltechworks.activities.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jrosario.challenge.criticaltechworks.R;
import com.jrosario.challenge.criticaltechworks.activities.article_details.ArticleDetailsActivity;
import com.jrosario.challenge.criticaltechworks.dialogs.LoadingDialog;
import com.jrosario.challenge.criticaltechworks.models.Article;
import com.jrosario.challenge.criticaltechworks.utils.NewsHeadlinesAdapter;
import com.jrosario.challenge.criticaltechworks.utils.OnItemClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener, MainContract.View {
    /* View Elements */
    private TextView tvNewsSource;
    private RecyclerView rvNewsHeadlines;
    private LoadingDialog loadingDialog;
    SwipeRefreshLayout swipeRefresh;

    /* Logic Elements */
    private NewsHeadlinesAdapter adapter;

    /* Architectural Elements */
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setupRV();

        loadingDialog = new LoadingDialog(this);
        mPresenter = new MainPresenter(getApplicationContext(),this);
        mPresenter.initP();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void initV() {
        loadingDialog.showDialog();
        setInfo();
    }

    @Override
    public void apiRequestError(String error) {
        loadingDialog.hideDialog();
        swipeRefresh.setRefreshing(false);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_error_info);

        TextView tvErrorText = dialog.findViewById(R.id.tv_error_message);
        tvErrorText.setText(error);

        Button btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void addArticles(List<Article> articles) {
        loadingDialog.hideDialog();
        swipeRefresh.setRefreshing(false);
        adapter.addArticles(articles);
    }

    @Override
    public void onItemClick(int pos) {
        Article article = adapter.getArticle(pos);
        Intent intent = new Intent(MainActivity.this, ArticleDetailsActivity.class);
        intent.putExtra("Article", article);
        startActivity(intent);
    }

    private void initView() {
        swipeRefresh = findViewById(R.id.swipeRefresh);
        tvNewsSource = findViewById(R.id.tv_news_source);
        rvNewsHeadlines = findViewById(R.id.rv_news_headlines);
    }

    private void setupRV() {
        swipeRefresh.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvNewsHeadlines.setLayoutManager(layoutManager);
        adapter = new NewsHeadlinesAdapter(this);
        rvNewsHeadlines.setAdapter(adapter);
    }

    private void setInfo() {
        tvNewsSource.setText(getString(R.string.news_source_name));
        getMoreArticles();
    }

    private void getMoreArticles() {
        mPresenter.getNewsHeadlines();
    }

    @Override
    public void onRefresh() {
        adapter.cleanArticles();
        getMoreArticles();
    }
}