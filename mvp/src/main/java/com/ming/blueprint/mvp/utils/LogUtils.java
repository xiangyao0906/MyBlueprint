package com.ming.blueprint.mvp.utils;

import android.util.Log;

import com.ming.blueprint.mvp.BuildConfig;

/**
 * Created by ming on 2017/8/10 10:46
 * description：
 */

public class LogUtils {

    private static final String TAG = "wtf";

    public static void v(String msg) {
        if (BuildConfig.DEBUG && msg != null) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG && msg != null) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG && msg != null) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG && msg != null) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG && msg != null) {
            Log.e(TAG, msg);
        }
    }

}
