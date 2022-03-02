package com.jrosario.challenge.criticaltechworks.activities.main;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jrosario.challenge.criticaltechworks.R;
import com.jrosario.challenge.criticaltechworks.adapters.NewsHeadlinesAdapter;
import com.jrosario.challenge.criticaltechworks.dialogs.LoadingDialog;
import com.jrosario.challenge.criticaltechworks.interfaces.OnItemClickListener;
import com.kwabenaberko.newsapilib.models.Article;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, MainContract.View {
    /* View Elements */
    private TextView tvNewsSource;
    private RecyclerView rvNewsHeadlines;
    private LoadingDialog loadingDialog;

    /* Logic Elements */
    private NewsHeadlinesAdapter adapter;
    private int page = 0;

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
    public void apiRequestError(Throwable error) {
        loadingDialog.hideDialog();

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_error_info);

        TextView tvErrorText = dialog.findViewById(R.id.tv_error_message);
        tvErrorText.setText(error.getLocalizedMessage());

        Button btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void addArticles(List<Article> articles) {
        adapter.addHeadlines(articles);
        loadingDialog.hideDialog();
    }

    @Override
    public void onItemClick(int pos) {

    }

    private void initView() {
        tvNewsSource = findViewById(R.id.tv_news_source);
        rvNewsHeadlines = findViewById(R.id.rv_news_headlines);
    }

    private void setupRV() {
        rvNewsHeadlines.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsHeadlinesAdapter(this);
        rvNewsHeadlines.setAdapter(adapter);
    }

    private void setInfo() {
        tvNewsSource.setText(getString(R.string.bbc_news));
        getMoreArticles();
    }

    private void getMoreArticles() {
        mPresenter.getNewsHeadlines(++page);
    }
}