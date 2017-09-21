package com.ming.blueprint.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ming on 2017/9/14.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private EditText name_et, pwd_et;
    private Button login_btn;

    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new LoginPresenter(this);
        name_et = findViewById(R.id.name_et);
        pwd_et = findViewById(R.id.pwd_et);
        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(view ->
                mPresenter.login(name_et.getText().toString().trim(), pwd_et.getText().toString().trim()));
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loginSuccess() {
        Snackbar.make(login_btn, "登陆成功！", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailure() {
        Snackbar.make(login_btn, "账户或密码错误！", Snackbar.LENGTH_SHORT).show();
    }
}
