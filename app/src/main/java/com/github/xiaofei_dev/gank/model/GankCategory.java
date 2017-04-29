package com.github.xiaofei_dev.gank.model;

import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/9.
 */

public final class GankCategory {

    //特定类型干货请求返回结果

    private boolean error;
    @SerializedName("results") private ArrayList<GankAPI> dataList;

    public boolean isError() {
        return error;
    }

    public ArrayList<GankAPI> getDataList() {
        return dataList;
    }
}
