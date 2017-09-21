package com.ming.blueprint.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ming.blueprint.mvp.utils.NetWorkUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ming on 2016/12/21 10:02.
 * Explain:
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 根view
     */
    protected View mRootView;
    /**
     * 页面状态管理器
     */
    private PageStateManager pageStateManager;
    /**
     * 是否已经有数据
     */
    private boolean isContentAlready;

    /**
     * 是否允许加载数据
     */
    public boolean isLoad;
    /**
     * 当前Fragment是否可见
     */
    public boolean isVisible;

    private Unbinder unbinder;
    private static ProgressDialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(initContentView(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initComponent(savedInstanceState);
        initListener();
        //组件初始化完毕，允许加载数据
        isLoad = true;
        if (isVisible) {
            //如果当前Fragment可见，加载数据
            initData(false);
        }
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
     * 添加监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据 子类需要重写该方法
     *
     * @param load 是否允许加载数据，默认只允许加载一次。
     *             如果想让某个Fragment每次在用户可见时都加载，可以在该子类方法super里传值true
     */
    protected void initData(boolean load) {
        isLoad = load;
    }

    /**
     * 数据加载失败重新加载
     */
    protected abstract void onDataReload();

    public void showLoadingDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
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
                if (NetWorkUtils.isNetWorkAvailable(getActivity())) {
                    pageStateManager.showLoading();
                    onDataReload();
                } else {
                    Toast.makeText(getActivity(), "网络不见了:-(", Toast.LENGTH_SHORT).show();
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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //当前Fragment可见
            isVisible = true;
            if (isLoad) {
                //允许加载数据
                initData(false);
            }
        }
    }

    protected <T extends View> T findViewById(int id) {
        if (mRootView == null) {
            return null;
        }
        return (T) mRootView.findViewById(id);
    }

    public void openActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(getActivity(), targetActivityClass);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), targetActivityClass);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        getActivity().finish();
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        getActivity().finish();
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void openNextFragment(int contentLayout, Fragment targetFragment, String TAG) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(contentLayout, targetFragment, TAG);
        transaction.commit();
    }

    public void openNextFragment(int contentLayout, Fragment targetFragment, String TAG, Bundle bundle) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        targetFragment.setArguments(bundle);
        transaction.add(contentLayout, targetFragment, TAG);
        transaction.commit();
    }

    public void openNextFragment(int contentLayout, Fragment targetFragment, String TAG, int message) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, message);
        targetFragment.setArguments(bundle);
        transaction.add(contentLayout, targetFragment, TAG);
        transaction.commit();
    }

}
