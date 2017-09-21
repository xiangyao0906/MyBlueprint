package com.ming.blueprint.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ming.blueprint.mvp.utils.NetWorkUtils;

import butterknife.ButterKnife;

/**
 * Created by ming on 2016/12/14 12:01.
 * Explain:
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog dialog = null;
    /**
     * 页面状态管理器
     */
    private PageStateManager pageStateManager;
    /**
     * 是否已经有数据
     */
    private boolean isContentAlready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //添加到Activity栈
        ActivityStackManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(null);

        setContentView(initContentView());

        ButterKnife.bind(this);

        initComponent(savedInstanceState);

        initListener();

        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void finish() {
        //移出Activity栈
        ActivityStackManager.getInstance().removeActivity(this);
        super.finish();
    }

    /**
     * 初始化布局资源文件
     */
    protected abstract int initContentView();

    /**
     * 初始化组件
     */
    protected abstract void initComponent(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 数据加载失败重新加载
     */
    protected abstract void onDataReload();

    public void showLoadingDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(BaseActivity.this);
            dialog.setMessage("请稍后...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            dialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 加载中状态
     *
     * @param container view
     *                  如果需要在该view上显示
     */
    public void showStateLoading(View container) {
        if (container != null) {
            pageStateManager = new PageStateManager(container);
            pageStateManager.showLoading();
        }
    }

    /**
     * 加载完成，显示内容
     */
    public void showStateContent() {
        if (pageStateManager != null) {
            pageStateManager.showContent();
            isContentAlready = true;
        }
    }

    /**
     * 加载失败状态
     */
    public void showStateError() {
        if (pageStateManager != null && !isContentAlready) {
            pageStateManager.showError();
            pageStateManager.setOnRetryClickListener(() -> {
                if (NetWorkUtils.isNetWorkAvailable(BaseActivity.this)) {
                    pageStateManager.showLoading();
                    onDataReload();
                } else {
                    Toast.makeText(BaseActivity.this, "网络不见了:-(", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 加载完成，没有数据状态
     */
    public void showStateEmpty() {
        if (pageStateManager != null) {
            pageStateManager.showEmpty();
        }
    }

    public void openActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(this, targetActivityClass);
        startActivity(intent);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        intent.putExtras(bundle);
        startActivity(intent);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        this.finish();
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        this.finish();
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
