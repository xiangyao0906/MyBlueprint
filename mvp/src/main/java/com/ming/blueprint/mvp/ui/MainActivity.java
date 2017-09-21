package com.ming.blueprint.mvp.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.ming.blueprint.mvp.R;
import com.ming.blueprint.mvp.base.BaseActivity;
import com.ming.blueprint.mvp.ui.activity.MovieActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.open_movie_btn)
    TextView openMovieBtn;

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        openMovieBtn.setOnClickListener(view -> openActivity(MovieActivity.class));
    }

    @Override
    protected void onDataReload() {

    }
}
