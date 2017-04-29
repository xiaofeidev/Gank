package com.github.xiaofei_dev.gank.retrofit;

//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/30.
 */

public final class RetrofitClient {

    private RetrofitClient() {}
    private static final OkHttpClient okHttpClient =new  OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();

    private static class ClientHolder {
        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.API_BASE)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return ClientHolder.retrofit;
    }
}
