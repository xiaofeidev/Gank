package com.github.xiaofei_dev.gank.presenter;

/**
 * Created by Administrator on 2017/4/9.
 */

public interface GankCategoryPresenter {

    void subscribeCategory(String category, int num, int page);

    void getMoreData(String category, int num, int page);

    //void unsubscribe();
}
