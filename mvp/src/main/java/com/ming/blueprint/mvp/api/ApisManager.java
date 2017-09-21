package com.ming.blueprint.mvp.api;

import com.ming.blueprint.mvp.BuildConfig;
import com.ming.blueprint.mvp.entity.Movie;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ming on 2017/7/24 15:07 16:41
 * description：
 */

public class ApisManager {

    private static volatile ApisManager instance;

    private ApisManager() {
    }

    public static ApisManager getInstance() {
        if (instance == null) {
            synchronized (ApisManager.class) {
                if (instance == null) {
                    instance = new ApisManager();
                }
            }
        }
        return instance;
    }

    private OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ?
                    HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.douban.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    private Apis apis = retrofit.create(Apis.class);

    public Call<Movie> getMovie(Map<String, String> map) {
        return apis.getMovie(map);
    }

//    public Observable<DataModel> getTopByPost(int start, int count) {
//        return apis.postTop250(start, count);
//    }

}
