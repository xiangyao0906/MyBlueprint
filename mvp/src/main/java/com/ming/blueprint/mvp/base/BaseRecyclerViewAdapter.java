package com.ming.blueprint.mvp.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ming.blueprint.mvp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ming on 2017/8/1 18:06
 * description：BaseRecyclerViewAdapter
 * <p>
 * 支持添加headerView
 * 添加footerView为private方法，如果需要支持可以改为public方法
 * 支持自动加载更多
 * 暂无适配GridLayoutManager，StaggeredGridLayoutManager
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_HEADER = 0;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;

    //是否可以自动加载
    private boolean isLoad = true;
    //是否正在刷新
    private boolean isLoading = true;
    //是否清空数据
    private boolean isClear;

    private List<T> mData = new ArrayList<>();

    private View headerView;
    private View footerView;

    private OnItemClickListener onItemClickListener;
    private OnLoadMoreListener onLoadMoreListener;

    private TextView message;

    public BaseRecyclerViewAdapter(Context context, RecyclerView view) {
        //滑动监听
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && onLoadMoreListener != null && isLoad && isLoading) {
                    //获取最后一个完全显示的ItemPosition 注意LinearLayoutManager，其他布局管理器需要判断
                    int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    //获取总的Count
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多
//                        if (isLoad && isLoading) {
                        getFooterView().findViewById(R.id.progressing).setVisibility(View.VISIBLE);
                        if (message != null) {
                            message.setText("loading...");
                        }
                        isLoading = false;
                        onLoadMoreListener.onLoad();
//                        }
                    }
                }
            }
        });
        addFooterView(LayoutInflater.from(context).inflate(R.layout.foot_view, view, false));
        message = getFooterView().findViewById(R.id.loading);
        message.setVisibility(View.GONE);
        getFooterView().findViewById(R.id.progressing).setVisibility(View.GONE);
    }

    public void addHeaderView(View headerView) {
        this.headerView = headerView;
//        notifyItemInserted(0);
    }

    private void addFooterView(View footerView) {
        this.footerView = footerView;
//        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return headerView;
    }

    public View getFooterView() {
        return footerView;
    }

    public void addData(List<T> data) {
        if (isClear) {
            mData.clear();
            isClear = false;
        }
        this.mData.addAll(data);
        notifyDataSetChanged();
        message.setVisibility(View.VISIBLE);
        if (isLoad) {
            getFooterView().findViewById(R.id.progressing).setVisibility(View.VISIBLE);
        }
        isLoading = true;
    }

    public void clearData() {
        isClear = true;
    }

    public void setRetry() {
        isLoading = true;
        message.setVisibility(View.VISIBLE);
        getFooterView().findViewById(R.id.progressing).setVisibility(View.GONE);
        message.setText("error, pull up to retry");
    }

    public void setLoadEnabled(boolean enabled) {
        isLoad = enabled;
        if (enabled) {
            getFooterView().findViewById(R.id.progressing).setVisibility(View.VISIBLE);
            message.setText("loading...");
        } else {
            getFooterView().findViewById(R.id.progressing).setVisibility(View.GONE);
            message.setText("_____ END _____");
        }
    }

    @Override
    public int getItemViewType(int position) {
        //如果有header并且position等于0 返回headerView
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        }
        //如果有header和footer时，最后footer的position应该等于size+1 返回footerView
        if (headerView != null && footerView != null && position == mData.size() + 1) {
            return TYPE_FOOTER;
        } else if (headerView == null && footerView != null && position == mData.size()) {
            //没有header，有footer，footer的position为size 返回footerView
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 如果是footer类型，返回footerView
        if (viewType == TYPE_FOOTER) {
            return new BaseViewHolder(footerView);
        }
        // 如果是header类型，返回headerView
        if (viewType == TYPE_HEADER) {
            return new BaseViewHolder(headerView);
        }
        return onCreateView(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        // 如有header，没有footer，position是第一个的时候return
        if (headerView != null && footerView == null) {
            if (position == 0) {
                return;
            }
        }
        // 如没有header，但有footer position是最后一个的时候return
        if (headerView == null && footerView != null) {
            if (position == mData.size()) {
                return;
            }
        }
        // 如有header，有footer position是第一个或最后一个的return
        if (headerView != null && footerView != null) {
            if (position == 0 || position == mData.size() + 1) {
                return;
            }
        }
        final int pos = getRealPosition(viewHolder);
        final T data = this.mData.get(pos);
        onBindView(viewHolder, pos, data);

        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(viewHolder.itemView, pos, data));
        }
    }

    //一般来说只有存在header的时候下标才会偏移
    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        if (footerView != null) {
            size++;
        }
        if (headerView != null) {
            size++;
        }
        return size;
    }

    public abstract RecyclerView.ViewHolder onCreateView(ViewGroup parent, final int viewType);

    public abstract void onBindView(RecyclerView.ViewHolder viewHolder, int position, T data);

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T data);
    }

    public interface OnLoadMoreListener {
        void onLoad();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}
