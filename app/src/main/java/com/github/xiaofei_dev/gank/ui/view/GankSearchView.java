package com.github.xiaofei_dev.gank.ui.view;

import android.support.annotation.Nullable;

import com.github.xiaofei_dev.gank.model.GankSearchResult;

/**
 * Created by Administrator on 2017/4/22.
 */

public interface GankSearchView {

    void setSearchInfo(GankSearchResult gankSearchResult);
    void setLoadMoreErr(boolean err);
    void loadMoreGankData(@Nullable GankSearchResult gankSearchResult);
    void showNetworkError();
}
