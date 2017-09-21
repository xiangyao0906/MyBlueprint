package com.ming.blueprint.dagger;

import com.ming.blueprint.dagger.base.BasePresenter;
import com.ming.blueprint.dagger.base.BaseView;

/**
 * Created by ming on 2017/9/14.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void loginSuccess();

        void loginFailure();

    }

    interface Presenter extends BasePresenter {
        void login(String name, String password);
    }

}
