package com.github.xiaofei_dev.gank.presenter.impl;

import android.support.annotation.NonNull;

import com.github.xiaofei_dev.gank.model.GankSearchResult;
import com.github.xiaofei_dev.gank.presenter.GankSearchPresenter;
import com.github.xiaofei_dev.gank.presenter.base.GankBasePresenter;
import com.github.xiaofei_dev.gank.retrofit.RetrofitClient;
import com.github.xiaofei_dev.gank.retrofit.RetrofitService;
import com.github.xiaofei_dev.gank.ui.fragment.GankSearchFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/22.
 */

public final class GankSearchPresenterImpl implements GankSearchPresenter,GankBasePresenter {

    @NonNull
    private final CompositeDisposable compositeDisposable;

    @NonNull
    private final GankSearchFragment mGankSearchFragment;

    public GankSearchPresenterImpl(@NonNull CompositeDisposable compositeDisposable,
                                   @NonNull GankSearchFragment gankSearchFragment) {
        this.compositeDisposable = compositeDisposable;
        mGankSearchFragment = gankSearchFragment;
    }

    @Override
    public void subscribeSearch(String key, int count, int page) {
        Disposable disposable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getSearchResult(key,count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankSearchResult>(){
                    @Override
                    public void onNext(GankSearchResult searchResult) {
                        mGankSearchFragment.setSearchInfo(searchResult);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mGankSearchFragment.showNetworkError();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void getMoreData(String key, int count, int page) {
        Disposable disposable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getSearchResult(key,count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankSearchResult>(){
                    @Override
                    public void onNext(GankSearchResult searchResult) {
                        mGankSearchFragment.setLoadMoreErr(true);
                        mGankSearchFragment.loadMoreGankData(searchResult);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mGankSearchFragment.setLoadMoreErr(false);
                        mGankSearchFragment.loadMoreGankData(null);
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
