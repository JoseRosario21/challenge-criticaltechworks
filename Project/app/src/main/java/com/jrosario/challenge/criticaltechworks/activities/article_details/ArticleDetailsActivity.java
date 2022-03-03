package com.jrosario.challenge.criticaltechworks.activities.article_details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jrosario.challenge.criticaltechworks.R;
import com.jrosario.challenge.criticaltechworks.models.Article;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ArticleDetailsActivity extends AppCompatActivity {
    /* Logic Elements */
    private Article article;

    /* View Elements */
    private TextView tvNewsSource;
    private TextView tvHeadlineTitle;
    private TextView tvPublishedDate;
    private TextView tvArticleContent;
    private TextView tvAuthorName;
    private ImageView ivImage;
    private ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        article = (Article) getIntent().getExtras().get("Article");

        initView();
        setInfo();
        addListeners();
    }

    private void initView() {
        tvNewsSource = findViewById(R.id.tv_news_source);
        tvHeadlineTitle = findViewById(R.id.tv_headline_title);
        tvPublishedDate = findViewById(R.id.tv_published_date);
        tvArticleContent = findViewById(R.id.tv_content);
        tvAuthorName = findViewById(R.id.tv_author_name);
        ivImage = findViewById(R.id.iv_headline_image);
        ivBackArrow = findViewById(R.id.iv_back_arrow);
    }

    private void setInfo() {
        tvNewsSource.setText(getString(R.string.news_source_name));

        tvHeadlineTitle.setText(article.getTitle());

        DateTime publishedDate = new DateTime(article.getPublishedAt());
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
        tvPublishedDate.setText(publishedDate.toString(dtf));

        tvArticleContent.setText(article.getDescription());

        tvAuthorName.setText(article.getAuthor());

        Picasso.get().load(article.getUrlToImage()).into(ivImage);
    }

    private void addListeners() {
        ivBackArrow.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }
}