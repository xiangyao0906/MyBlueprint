package com.ming.blueprint.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ming.blueprint.mvp.adapter.MovieAdapter;
import com.ming.blueprint.mvp.api.ApisManager;
import com.ming.blueprint.mvp.base.BaseRecyclerViewAdapter;
import com.ming.blueprint.mvp.contract.MovieContract;
import com.ming.blueprint.mvp.entity.Movie;
import com.ming.blueprint.mvp.utils.LogUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ming on 2017/9/15 15:35.
 * Desc:
 */

public class MoviePresenter implements MovieContract.Presenter {

    private MovieContract.View movieView;
    private Call<Movie> call;
    private MovieAdapter adapter;
    private Context context;
    private RecyclerView recyclerView;
    private int page = 1;

    public MoviePresenter(MovieContract.View movieView, Context context, RecyclerView view) {
        LogUtils.i("MoviePresenter");
        this.movieView = movieView;
        this.context = context;
        this.recyclerView = view;
        movieView.setPresenter(this);
    }

    @Override
    public void start() {
        LogUtils.i("start");
        adapter = new MovieAdapter(context, recyclerView);
        adapter.setOnLoadMoreListener(()->getData());
        movieView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("start", "0");
        map.put("count", "10");
        call = ApisManager.getInstance().getMovie(map);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieView.movieSuccess();
                page++;
                if (page > 3) {
                    adapter.setLoadEnabled(false);
                }
                adapter.addData(response.body().getSubjects());
                movieView.closeProgress();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable throwable) {
                if (call.isCanceled()) {
                    LogUtils.e("取消数据请求");
                } else {
                    adapter.setRetry();
                    movieView.movieFailure();
                }
            }
        });
    }

    @Override
    public void movieCancel() {
        call.cancel();
    }

    @Override
    public void movieRefresh() {
        page = 1;
        adapter.clearData();
        adapter.setLoadEnabled(true);
        getData();
    }

    @Override
    public void reload() {
        getData();
    }
}
