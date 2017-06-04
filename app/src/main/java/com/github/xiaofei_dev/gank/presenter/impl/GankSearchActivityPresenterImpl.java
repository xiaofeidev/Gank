package com.github.xiaofei_dev.gank.presenter.impl;

import android.support.annotation.NonNull;

import com.github.xiaofei_dev.gank.model.GankSearchResult;
import com.github.xiaofei_dev.gank.presenter.GankSearchPresenter;
import com.github.xiaofei_dev.gank.presenter.base.GankBasePresenter;
import com.github.xiaofei_dev.gank.retrofit.RetrofitClient;
import com.github.xiaofei_dev.gank.retrofit.RetrofitService;
import com.github.xiaofei_dev.gank.ui.activity.SearchActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author：xiaofei_dev
 * time：2017/4/28:21:46
 * e-mail：xiaofei.dev@gmail.com
 * desc：coding
 */
public final class GankSearchActivityPresenterImpl implements GankSearchPresenter,GankBasePresenter {

    @NonNull
    private final CompositeDisposable compositeDisposable;

    @NonNull
    private final SearchActivity mSearchActivity;

    public GankSearchActivityPresenterImpl(@NonNull CompositeDisposable compositeDisposable,
                                   @NonNull SearchActivity searchActivity) {
        this.compositeDisposable = compositeDisposable;
        mSearchActivity = searchActivity;
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
                        mSearchActivity.setSearchInfo(searchResult);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSearchActivity.showNetworkError();

                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.clear();
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
                        mSearchActivity.setLoadMoreErr(true);
                        mSearchActivity.loadMoreGankData(searchResult);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mSearchActivity.setLoadMoreErr(false);
                        mSearchActivity.loadMoreGankData(null);
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
