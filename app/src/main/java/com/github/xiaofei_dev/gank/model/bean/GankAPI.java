package com.github.xiaofei_dev.gank.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

//类名比 GankAPI 多了个单词 Images 是因为此类多了个字段 images
public final class GankAPI {
    /**
     * id : 58dc6a34421aa969fd8a3dec
     * createdAt : 2017-03-30T10:15:16.537Z
     * desc : iOS Material Design 风格的动画库，做的好细腻，我给满分。
     * images : ["http://img.gank.io/627220ba-4e59-4c7e-849a-4b897a094588"]
     * publishedAt : 2017-03-30T11:46:55.192Z
     * source : chrome
     * type : iOS
     * url : https://github.com/material-motion/material-motion-swift
     * used : true
     * who : 代码家
     */

    @SerializedName("_id")
    @Expose
    public String id;
    @Expose public String createdAt;
    @Expose public String desc;
    @Expose public String publishedAt;
    @Expose public String source;
    @Expose public String type;
    @Expose public String url;
    @Expose public boolean used;
    @Expose public String who;
    @Expose public List<String> images;

}
