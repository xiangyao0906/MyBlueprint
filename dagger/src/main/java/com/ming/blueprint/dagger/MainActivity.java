package com.ming.blueprint.dagger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ming.blueprint.dagger.dagger.bean.User;
import com.ming.blueprint.dagger.dagger.component.DaggerUserComponent;
import com.ming.blueprint.dagger.dagger.component.UserComponent;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    @Inject
    User user;

    private UserComponent userComponent;

    private TextView message_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message_tv = findViewById(R.id.message_tv);

        userComponent = DaggerUserComponent.builder().build();
        userComponent.inject(this);
        message_tv.setText(user.getName() + "：" + user.getSex());
        message_tv.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class)));
    }
}
