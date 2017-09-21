package com.ming.blueprint.mvp.contract;

import com.ming.blueprint.mvp.adapter.MovieAdapter;
import com.ming.blueprint.mvp.base.BasePresenter;
import com.ming.blueprint.mvp.base.BaseView;

/**
 * Created by ming on 2017/9/15 15:18.
 * Desc:
 */

public interface MovieContract {
    interface Presenter extends BasePresenter {
        void movieCancel();

        void movieRefresh();

        void reload();
    }

    interface View extends BaseView<Presenter> {

        void setAdapter(MovieAdapter adapter);

        void movieSuccess();

        void movieFailure();

        void closeProgress();

    }
}
