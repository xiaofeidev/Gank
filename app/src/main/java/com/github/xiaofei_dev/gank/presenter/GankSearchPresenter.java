package com.github.xiaofei_dev.gank.presenter;


/**
 * Created by Administrator on 2017/4/22.
 */

public interface GankSearchPresenter {

    void subscribeSearch(String key, int count, int page);

    void getMoreData(String key, int count, int page);

}
