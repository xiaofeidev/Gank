package com.github.xiaofei_dev.gank.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;

/**
 * Created by Administrator on 2017/4/19.
 */

public final class GankDayBean implements MultiItemEntity {
    public static final int DATE = 1;
    public static final int CATEGORY = 2;
    public static final int ITEM = 3;

    private int itemType;
    public String content;
    public GankAPI mGankAPI;

    public GankDayBean(int itemType, String content) {
        this.itemType = itemType;
        this.content = content;
    }

    public GankDayBean(int itemType, GankAPI gankAPI) {
        this.itemType = itemType;
        mGankAPI = gankAPI;
    }

    public String getContent() {
        return content;
    }

    public GankAPI getGankAPI() {
        return mGankAPI;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
