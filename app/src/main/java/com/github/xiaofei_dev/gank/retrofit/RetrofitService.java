package com.github.xiaofei_dev.gank.retrofit;

import com.github.xiaofei_dev.gank.model.GankCategory;
import com.github.xiaofei_dev.gank.model.GankDay;
import com.github.xiaofei_dev.gank.model.GankRandom;
import com.github.xiaofei_dev.gank.model.GankSearchResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/3/30.
 */

public interface RetrofitService {

    @GET(API.API_DAY + "{date}")
    Observable<GankDay> getGankDay(@Path("date") String date);

    /*@GET(API.API_DATA + "Android/{num}/{page}")
    Observable<GankAndroid> getAndroid(@Path("num") int num,@Path("page") int page);

    @GET(API.API_DATA + "iOS/{num}/{page}")
    Observable<GankIOS> getIOS(@Path("num") int num, @Path("page") int page);*/

    @GET(API.API_DATA + "{category}/{num}/{page}")
    Observable<GankCategory> getCategory(@Path("category") String category,@Path("num") int num,
                                         @Path("page") int page);

    @GET(API.API_RANDOM + "{category}/{num}")
    Observable<GankRandom> getRandomData(@Path("category") String category,@Path("num") int num);

    @GET(API.API_SEARCH + "query/{key}/category/all/count/{count}/page/{page}")
    Observable<GankSearchResult> getSearchResult(@Path("key") String key, @Path("count") int count, @Path("page") int page);

}
