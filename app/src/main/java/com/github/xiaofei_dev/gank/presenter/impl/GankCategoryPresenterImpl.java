package com.github.xiaofei_dev.gank.presenter.impl;

import android.support.annotation.NonNull;

import com.github.xiaofei_dev.gank.model.GankCategory;
import com.github.xiaofei_dev.gank.presenter.base.GankBasePresenter;
import com.github.xiaofei_dev.gank.presenter.GankCategoryPresenter;
import com.github.xiaofei_dev.gank.retrofit.RetrofitClient;
import com.github.xiaofei_dev.gank.retrofit.RetrofitService;
import com.github.xiaofei_dev.gank.ui.view.GankCategoryView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/9.
 */

public final class GankCategoryPresenterImpl implements GankCategoryPresenter,GankBasePresenter {

    @NonNull
    private final CompositeDisposable compositeDisposable;

    @NonNull
    private final GankCategoryView mGankCategoryView;
    private static final String TAG = "GankCategoryPresenterImpl";

    public GankCategoryPresenterImpl(@NonNull CompositeDisposable compositeDisposable,
                                     @NonNull GankCategoryView gankCategoryView) {
        this.compositeDisposable = compositeDisposable;
        mGankCategoryView = gankCategoryView;
    }

    @Override
    public void subscribeCategory(String category, int num, int page) {
        Disposable disposable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getCategory(category,num,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankCategory>(){
                    @Override
                    public void onNext(GankCategory gankCategory) {
                        mGankCategoryView.setGankDataInfo(gankCategory);
                        //Log.d(TAG, "onNext: " + gankCategory.getDataList().toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        mGankCategoryView.showNetworkError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void getMoreData(String category, int num, int page) {
        compositeDisposable.clear();
        Disposable disposable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getCategory(category,num,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankCategory>(){
                    @Override
                    public void onNext(GankCategory gankCategory) {
                        mGankCategoryView.setLoadMoreErr(true);
                        mGankCategoryView.loadMoreGankData(gankCategory);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mGankCategoryView.setLoadMoreErr(false);
                        mGankCategoryView.loadMoreGankData(null);
                        //((GankBaseView)mGankCategoryView).loadMoreDateFail();
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
