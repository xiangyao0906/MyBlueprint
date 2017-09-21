package com.ming.blueprint.dagger;

/**
 * Created by ming on 2017/9/14.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;

    public LoginPresenter(LoginContract.View loginView) {
        loginView.setPresenter(this);
        mLoginView = loginView;
    }

    @Override
    public void start() {
    }

    @Override
    public void login(String name, String password) {
        if (!name.equals("123")) {
            mLoginView.loginFailure();
            return;
        }
        if (!password.equals("111")) {
            mLoginView.loginFailure();
            return;
        }
        if (name.equals("123") && password.equals("111")) {
            mLoginView.loginSuccess();
        }
    }
}
