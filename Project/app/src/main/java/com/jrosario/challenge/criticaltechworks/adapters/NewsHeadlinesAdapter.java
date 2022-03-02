package com.jrosario.challenge.criticaltechworks.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jrosario.challenge.criticaltechworks.R;
import com.jrosario.challenge.criticaltechworks.interfaces.OnItemClickListener;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsHeadlinesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> articles = new ArrayList<>();
    private final OnItemClickListener listener;

    private final int VIEW_TYPE_NO_HEADLINES = 0;
    private final int VIEW_TYPE_HEADLINE = 1;

    public NewsHeadlinesAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADLINE)
            return new NewsHeadlinesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_headline, parent, false));
        else
            return new NoHeadlinesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_more_headlines, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHeadlinesViewHolder) {
            ((NewsHeadlinesViewHolder) holder).getTvHeadlineTitle().setText(articles.get(position).getTitle());
            Picasso.get().load(articles.get(position).getUrlToImage()).into(((NewsHeadlinesViewHolder) holder).getIvImage());
        }
    }

    @Override
    public int getItemCount() {
        return articles.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (articles.size() == 0)
            return  VIEW_TYPE_NO_HEADLINES;
        else if (position == articles.size())
            return  VIEW_TYPE_NO_HEADLINES;
        else
            return VIEW_TYPE_HEADLINE;
    }

    public static class NewsHeadlinesViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvHeadlineTitle;
        private final ImageView ivImage;

        public NewsHeadlinesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeadlineTitle = itemView.findViewById(R.id.tv_headline_title);
            ivImage = itemView.findViewById(R.id.iv_headline_image);
        }

        public TextView getTvHeadlineTitle() {
            return tvHeadlineTitle;
        }

        public ImageView getIvImage() {
            return ivImage;
        }
    }

    public static class NoHeadlinesViewHolder extends RecyclerView.ViewHolder {
        public NoHeadlinesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addHeadlines(List<Article> articles) {
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }
}