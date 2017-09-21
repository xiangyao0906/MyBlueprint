package com.ming.blueprint.mvp.api;

import com.ming.blueprint.mvp.entity.Movie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ming on 2017/7/24 16:32 16:40
 * description：
 */
interface Apis {


    @GET("v2/movie/top250?")
    Call<Movie> getMovie(@QueryMap() Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("v2/movie/top250?")
//    Observable<DataModel> postTop250(@Field("start") int start,
//                                     @Field("count") int count);

}
