package com.ming.blueprint.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ming.blueprint.mvp.R;
import com.ming.blueprint.mvp.adapter.MovieAdapter;
import com.ming.blueprint.mvp.base.BaseActivity;
import com.ming.blueprint.mvp.contract.MovieContract;
import com.ming.blueprint.mvp.presenter.MoviePresenter;
import com.ming.blueprint.mvp.utils.LogUtils;

import butterknife.BindView;

/**
 * Created by ming on 2017/9/15 14:41.
 * Desc:
 */

public class MovieActivity extends BaseActivity implements MovieContract.View {

    @BindView(R.id.movie_list)
    RecyclerView movieList;
    @BindView(R.id.movie_toolbar)
    Toolbar movieToolbar;
    @BindView(R.id.movie_refresh)
    SwipeRefreshLayout movieRefresh;

    private MovieContract.Presenter presenter;

    @Override
    protected int initContentView() {
        LogUtils.i("initContentView");
        return R.layout.activity_movie;
    }

    @Override
    protected void initComponent(Bundle savedInstanceState) {
        LogUtils.i("initComponent");
        showStateLoading(movieList);
        new MoviePresenter(this, this, movieList);
        movieList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        LogUtils.i("initData");
        presenter.start();
    }

    @Override
    protected void initListener() {
        LogUtils.i("initListener");
        movieToolbar.setNavigationOnClickListener(view -> finish());
        movieRefresh.setOnRefreshListener(() -> presenter.movieRefresh());
    }

    @Override
    protected void onDataReload() {
        LogUtils.i("onDataReload");
        presenter.reload();
    }

    @Override
    public void setPresenter(MovieContract.Presenter presenter) {
        LogUtils.i("setPresenter");
        this.presenter = presenter;
    }

    @Override
    public void setAdapter(MovieAdapter adapter) {
        movieList.setAdapter(adapter);
    }

    @Override
    public void movieSuccess() {
        LogUtils.i("movieSuccess");
        showStateContent();
    }

    @Override
    public void movieFailure() {
        LogUtils.i("movieFailure");
        showStateError();
        movieRefresh.setRefreshing(false);
    }

    @Override
    public void closeProgress() {
        LogUtils.i("closeProgress");
        movieRefresh.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        LogUtils.i("onDestroy");
        presenter.movieCancel();
        super.onDestroy();
    }

}
