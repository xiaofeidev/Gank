package com.github.xiaofei_dev.gank.ui.view;

import android.support.annotation.Nullable;

import com.github.xiaofei_dev.gank.model.GankCategory;

/**
 * Created by Administrator on 2017/4/9.
 */

public interface GankCategoryView {

    void setGankDataInfo(GankCategory gankCategory);
    //void firstGetDate();
    void loadMoreGankData(@Nullable GankCategory gankCategory);
    void setLoadMoreErr(boolean err);
    void showNetworkError();
}
