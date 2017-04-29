package com.github.xiaofei_dev.gank.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/22.
 */

public final class GankSearchResultBean {
    @SerializedName("ganhuo_id")
    @Expose
    public String id;
    @Expose public String desc;
    @Expose public String publishedAt;
    @Expose public String readability;
    @Expose public String type;
    @Expose public String url;
    @Expose public String who;
}
