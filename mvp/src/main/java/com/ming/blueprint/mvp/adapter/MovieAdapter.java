package com.ming.blueprint.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ming.blueprint.mvp.R;
import com.ming.blueprint.mvp.base.BaseRecyclerViewAdapter;
import com.ming.blueprint.mvp.entity.Movie;

import java.util.List;

/**
 * Created by ming on 2017/9/15 15:58.
 * Desc:
 */

public class MovieAdapter extends BaseRecyclerViewAdapter<Movie> {

    private Context context;
    private LayoutInflater layoutInflater;

    public MovieAdapter(Context context, RecyclerView view) {
        super(context, view);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        return new MovieViewHolder(layoutInflater.inflate(R.layout.item_movie_list, parent, false));
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder viewHolder, int position, Movie data) {
        if (viewHolder instanceof MovieViewHolder) {
            MovieViewHolder holder = (MovieViewHolder) viewHolder;
            Glide.with(context).load(data.getImages().getLarge()).into(holder.movie_img);
            holder.name.setText(data.getTitle());
            holder.director.setText("年代：" + data.getYear());
            holder.performer.setText("类型：" + TextUtils.join("、", data.getGenres()));
        }
    }

    private static class MovieViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {
        ImageView movie_img;
        TextView name, director, performer;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movie_img = itemView.findViewById(R.id.movie_img);
            name = itemView.findViewById(R.id.movie_name);
            director = itemView.findViewById(R.id.movie_director);
            performer = itemView.findViewById(R.id.movie_performer);
        }
    }

}
