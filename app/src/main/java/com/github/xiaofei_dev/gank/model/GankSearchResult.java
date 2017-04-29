package com.github.xiaofei_dev.gank.model;

import com.github.xiaofei_dev.gank.model.bean.GankSearchResultBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/22.
 */

public final class GankSearchResult {
    public int count;
    public boolean error;
    @SerializedName("results")
    public ArrayList<GankSearchResultBean> dataList;
}
