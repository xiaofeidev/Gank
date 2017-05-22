package com.github.xiaofei_dev.gank.presenter.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.xiaofei_dev.gank.model.GankDay;
import com.github.xiaofei_dev.gank.presenter.base.GankBasePresenter;
import com.github.xiaofei_dev.gank.presenter.GankDayPresenter;
import com.github.xiaofei_dev.gank.retrofit.RetrofitClient;
import com.github.xiaofei_dev.gank.retrofit.RetrofitService;
import com.github.xiaofei_dev.gank.ui.view.GankDayView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/4/1.
 */

public final class GankDayPresenterImpl implements GankDayPresenter,GankBasePresenter {

    private static final String TAG = "GankDayPresenterImpl";
    @NonNull
    private final CompositeDisposable compositeDisposable;

    @NonNull
    /**
     * 请忽略此条注释。。。。。。。
    * 此处依照经验应该该持有 MainFragment 实现的接口 GankDayView 的引用
    * 然而在嵌入式系统中，调用一个接口的引用会比调用实体类的引用多花费一倍的时间
    * 而且 GankDayView 接口只有 MainFragment 一个实现类
    * 因此我们直接使用 MainFragment
    * 项目中其他地方同样是这个原因
    */
    private final GankDayView mGankDayView;

    public GankDayPresenterImpl(@NonNull CompositeDisposable compositeDisposable,
                                @NonNull GankDayView gankDayView) {
        this.compositeDisposable = compositeDisposable;
        mGankDayView = gankDayView;
    }

    @Override
    public void subscribeDay(String date) {
        Disposable disposable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getGankDay(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankDay>() {
                    @Override
                    public void onNext(GankDay gankDay){
                        if(/*gankDay.getResults().androidList != null && */!gankDay.getCategory().isEmpty()/*size() != 0*/){
                            mGankDayView.setGankDayInfo(gankDay);
                            //Log.d(TAG, "onNext: "+gankDay.getResults().toString());
                        }else {
                            mGankDayView.reGetData();
                        }
                        Log.d(TAG, "onNext: "+gankDay.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mGankDayView.showNetworkError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMoreData(String date) {
        Disposable disposable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getGankDay(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankDay>() {
                    @Override
                    public void onNext(GankDay gankDay){
                        if(!gankDay.getCategory().isEmpty()){
                            mGankDayView.setLoadMoreErr(true);
                            mGankDayView.loadMoreGankData(gankDay);
                        }else {
                            mGankDayView.getMoreData();
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mGankDayView.setLoadMoreErr(false);
                        mGankDayView.loadMoreGankData(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
