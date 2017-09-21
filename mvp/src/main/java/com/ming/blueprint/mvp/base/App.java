package com.ming.blueprint.mvp.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ming on 2017/9/15 14:04.
 * Desc:
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

    }
}
