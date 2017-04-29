package com.github.xiaofei_dev.gank.ui.view;

import android.support.annotation.Nullable;

import com.github.xiaofei_dev.gank.model.GankDay;

/**
 * Created by Administrator on 2017/3/30.
 */

public interface GankDayView {

    void setGankDayInfo(GankDay gankDay);
    void loadMoreGankData(@Nullable GankDay gankDay);
    void setLoadMoreErr(boolean err);
    void reGetData();
    void getMoreData();
    void showNetworkError();
}
