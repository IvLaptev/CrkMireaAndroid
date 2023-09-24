package com.ivlaptev.myapplication.activities.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ivlaptev.myapplication.R;
import com.ivlaptev.myapplication.activities.ArticleActivity;
import com.ivlaptev.myapplication.models.articles.ArticleCompact;
import com.squareup.picasso.Picasso;

import java.util.List;

class ArticlesStateAdapter extends RecyclerView.Adapter<ArticlesStateAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<ArticleCompact> states;
    private final FragmentActivity activity;

    ArticlesStateAdapter(Context context, List<ArticleCompact> states, FragmentActivity activity) {
        this.inflater = LayoutInflater.from(context);
        this.states = states;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.article_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleCompact state = states.get(position);
        System.out.println("+++++++++++");
        Log.i("TAG", state.getImage());
        Picasso.get().load(state.getImage()).placeholder(R.drawable.ic_ckmirea_logo).into(holder.coverView);
        holder.title.setText(state.getTitle());
        holder.category.setText(state.getCategory().getTitle());
        holder.author.setText(state.getAuthor().getLastname() + " "
                + state.getAuthor().getName().charAt(0) + "."
                + state.getAuthor().getMiddlename().charAt(0) + ".");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ArticleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("article_id", state.getId());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView coverView;
        final TextView title;
        final TextView author;
        final TextView category;
        final LinearLayout card;

        public ViewHolder(View itemView) {
            super(itemView);
            coverView = itemView.findViewById(R.id.itemCover);
            title = itemView.findViewById(R.id.title);
            card = itemView.findViewById(R.id.card);
            author = itemView.findViewById(R.id.author);
            category = itemView.findViewById(R.id.category);
        }
    }
}

