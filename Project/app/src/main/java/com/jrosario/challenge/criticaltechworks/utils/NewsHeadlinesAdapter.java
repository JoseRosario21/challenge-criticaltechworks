package com.jrosario.challenge.criticaltechworks.utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jrosario.challenge.criticaltechworks.R;
import com.jrosario.challenge.criticaltechworks.models.Article;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class NewsHeadlinesAdapter extends RecyclerView.Adapter<NewsHeadlinesAdapter.ArticleViewHolder> {
    private final List<Article> articles = new ArrayList<>();
    private final OnItemClickListener listener;

    public NewsHeadlinesAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_headline, parent, false), listener);

    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.getTvHeadlineTitle().setText(articles.get(position).getTitle());

        DateTime publishedDate = new DateTime(articles.get(position).getPublishedAt());
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        holder.getTvPublishedDate().setText(publishedDate.toString(dtf));

        Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.getIvImage());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public Article getArticle(int pos) {
        return articles.get(pos);
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvHeadlineTitle;
        private final TextView tvPublishedDate;
        private final ImageView ivImage;

        public ArticleViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvHeadlineTitle = itemView.findViewById(R.id.tv_headline_title);
            tvPublishedDate = itemView.findViewById(R.id.tv_published_date);
            ivImage = itemView.findViewById(R.id.iv_headline_image);

            itemView.setOnClickListener(v -> listener.onItemClick(getAbsoluteAdapterPosition()));
        }

        public TextView getTvHeadlineTitle() {
            return tvHeadlineTitle;
        }

        public TextView getTvPublishedDate() {
            return tvPublishedDate;
        }

        public ImageView getIvImage() {
            return ivImage;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addArticles(List<Article> articles) {
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void cleanArticles() {
        this.articles.clear();
        notifyDataSetChanged();
    }
}