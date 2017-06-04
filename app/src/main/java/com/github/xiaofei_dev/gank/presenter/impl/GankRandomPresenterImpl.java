package com.github.xiaofei_dev.gank.presenter.impl;

import android.support.annotation.NonNull;

import com.github.xiaofei_dev.gank.model.GankRandom;
import com.github.xiaofei_dev.gank.presenter.base.GankBasePresenter;
import com.github.xiaofei_dev.gank.presenter.GankRandomPresenter;
import com.github.xiaofei_dev.gank.retrofit.RetrofitClient;
import com.github.xiaofei_dev.gank.retrofit.RetrofitService;
import com.github.xiaofei_dev.gank.ui.view.GankRandomView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/20.
 */

public final class GankRandomPresenterImpl implements GankRandomPresenter,GankBasePresenter {

    @NonNull
    private final CompositeDisposable compositeDisposable;

    @NonNull
    private final GankRandomView mGankRandomView;

    public GankRandomPresenterImpl(@NonNull CompositeDisposable compositeDisposable,
                                   @NonNull GankRandomView gankRandomView) {
        this.compositeDisposable = compositeDisposable;
        mGankRandomView = gankRandomView;
    }

    @Override
    public void subscribeRandom(String category, int num) {
        Disposable disposable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getRandomData(category,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankRandom>(){
                    @Override
                    public void onNext(GankRandom gankRandom) {
                        mGankRandomView.setGankRandomInfo(gankRandom);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mGankRandomView.showNetworkError();
                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.clear();
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
